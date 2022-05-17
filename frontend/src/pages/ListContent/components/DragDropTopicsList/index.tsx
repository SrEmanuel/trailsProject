import { DragDropContext, Droppable, Draggable } from "react-beautiful-dnd";
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
        console.log(result)
      }}
    >
      <Droppable droppableId="topics">
        {(provided) => (
          <div {...provided.droppableProps} ref={provided.innerRef}>
            {topics?.map((topic, index) => (
              <Draggable
                draggableId={topic.name}
                index={index}
                key={topic.id}
              >
                {(provided) => (
                  <div
                    ref={provided.innerRef}
                    {...provided.draggableProps}
                    {...provided.dragHandleProps}
                  >
                    <Topic
                      topic={topic}
                      params={params}
                      enableAdminMode={true}
                      onDeleteSubject={onDeleteSubject}
                    />
                  </div>
                )}
              </Draggable>
            ))}
          </div>
        )}
      </Droppable>
    </DragDropContext>
  );
}
