import { DragDropContext, Droppable } from "react-beautiful-dnd";
import { Topic } from "../../../../components/Topic";
import { ISubject } from "../../../../interfaces/subject";
import { ITopic } from "../../../../interfaces/topic";

interface Props {
  topics: ITopic[];
  params: any;
  onContentChange: () => void;
  setTopics: React.Dispatch<React.SetStateAction<ITopic[] | undefined>>;
}

export function DragDropTopicsList({
  topics,
  setTopics,
  params,
  onContentChange,
}: Props) {
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

  return (
    <DragDropContext
      onDragEnd={(result) => {
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

        topics.splice(index, 1, newTopic);

        setTopics(topics);
      }}
    >
      {topics?.map((topic, index) => (
        <Droppable
          droppableId={String(index)}
          key={topic.id}
          direction="horizontal"
        >
          {(provided) => (
            <div {...provided.droppableProps} ref={provided.innerRef}>
              <Topic
                topic={topic}
                params={params}
                enableAdminMode={true}
                onDeleteSubject={onContentChange}
              />

              {provided.placeholder}
            </div>
          )}
        </Droppable>
      ))}
    </DragDropContext>
  );
}
