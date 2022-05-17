import { DragDropContext, Droppable } from "react-beautiful-dnd";
import { Topic } from "../../../../components/Topic";
import { ITopic } from "../../../../interfaces/topic";

interface Props {
  topics: ITopic[];
  params: any;
  onDeleteSubject: () => void;
}

export function DragDropTopicsList({ topics, params, onDeleteSubject }: Props) {
  return (
    <DragDropContext
      onDragEnd={(result, provided) => {
        console.log(result);
      }}
    >
      {topics?.map((topic, index) => (
        <Droppable
          droppableId={String(topic.id)}
          key={topic.id}
          direction="horizontal"
        >
          {(provided) => (
            <div {...provided.droppableProps} ref={provided.innerRef}>
              <Topic
                topic={topic}
                params={params}
                enableAdminMode={true}
                onDeleteSubject={onDeleteSubject}
              />

              {provided.placeholder}
            </div>
          )}
        </Droppable>
      ))}
    </DragDropContext>
  );
}
