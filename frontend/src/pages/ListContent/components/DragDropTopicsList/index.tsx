import { useState } from "react";
import { DragDropContext, Droppable, DropResult } from "react-beautiful-dnd";
import { useNavigate } from "react-router-dom";
import { Topic } from "../../../../components/Topic";
import { useAuth } from "../../../../hooks/useAuth";
import { ISubject } from "../../../../interfaces/subject";
import { ITopic } from "../../../../interfaces/topic";
import api from "../../../../services/api";
import { handleNotifyError } from "../../../../utils/handleNotifyError";

interface Props {
  topics: ITopic[];
  params: any;
  onContentChange: () => void | Promise<void>;
  setTopics: React.Dispatch<React.SetStateAction<ITopic[] | undefined>>;
}

interface UpdatePositionPayload {
  subjectId: string | number;
  position: number;
}

export function DragDropTopicsList({
  params,
  topics,
  onContentChange,
  setTopics,
}: Props) {
  const navigate = useNavigate();
  const { handleClearUserDataFromStorage } = useAuth();
  const [currentSourceId, setCurrentSourceId] = useState<string>();

  const reorder = (
    subjects: ISubject[],
    startIndex: number,
    endIndex: number
  ) => {
    const result = Array.from(subjects);
    const [removed] = result.splice(startIndex, 1);
    result.splice(endIndex, 0, removed);

    return result;
  };

  const replace = (list: any[], index: number, newItem: any) => {
    const newArray = Array.from(list);
    newArray.splice(index, 1);
    newArray.splice(index, 0, newItem);

    return newArray;
  };

  const updateList = async (
    linkName: string,
    data: UpdatePositionPayload[]
  ) => {
    try {
      await api.post(`/topics/${linkName}/update-positions`, data);
      await onContentChange();
    } catch (error) {
      handleNotifyError(error, navigate, handleClearUserDataFromStorage);
    }
  };

  const onDragEnd = (result: DropResult) => {
    if (!result.destination) {
      return;
    }

    const index = Number(result.source.droppableId);

    const selectedTopic = topics[index];

    const reorderedSubjects = reorder(
      selectedTopic.subjects,
      result.source.index,
      result.destination.index
    );

    const newTopic = { ...selectedTopic, subjects: reorderedSubjects };

    const newTopicList: ITopic[] = replace(topics, index, newTopic);

    const data: UpdatePositionPayload[] = [];

      newTopic.subjects.forEach((subject, index) =>{
        data.push({ subjectId: subject.id, position: index })
      }
      );

    setTopics(newTopicList);

    updateList(selectedTopic.linkName, data);
  };

  return (
    <DragDropContext
      onDragEnd={onDragEnd}
      onDragUpdate={(initial, provided) => {
        setCurrentSourceId(initial.source?.droppableId);
      }}
    >
      {topics?.map((topic, index) => (
        <Droppable
          droppableId={String(index)}
          key={topic.id}
          direction={window.screen.width >= 540 ? "horizontal" : "vertical"}
          isDropDisabled={JSON.stringify(index) !== currentSourceId}
        >
          {(provided, snapshot) => (
              <div
                className="dropabble-wrapper"
                {...provided.droppableProps}
                ref={provided.innerRef}
              >
                <Topic
                  topic={topic}
                  params={params}
                  enableAdminMode={true}
                  onChange={onContentChange}
                />
                {provided.placeholder}
              </div>
          )}
        </Droppable>
      ))}
    </DragDropContext>
  );
}
