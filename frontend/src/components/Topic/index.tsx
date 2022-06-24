import { FormEvent, Fragment, useState } from "react";
import { Draggable } from "react-beautiful-dnd";
import { FiEdit, FiTrash2 } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { useAuth } from "../../hooks/useAuth";
import { ITopic } from "../../interfaces/topic";
import api from "../../services/api";
import { handleNotifyError } from "../../utils/handleNotifyError";
import { reorderSubjects } from "../../utils/reoarderSubjects";
import { AddOrUpdateSection } from "../AddOrUpdateSection";
import { ConfirmationModal } from "../ConfirmationModal";
import { PlusButton } from "../PlusButton";
import { Subject } from "../Subject";

import "./styles.scss";

interface Props {
  topic: ITopic;
  params: any;
  enableAdminMode: boolean;
  onChange: () => void;
}

export function Topic({ topic, params, enableAdminMode, onChange }: Props) {
  const [isDeleteModalVisible, setIsDeleteModalVisible] = useState(false);
  const [isTopicBeingDeleted, setIsTopicBeingDeleted] =
    useState<boolean>(false);
  const [selectedSubject, setSelectedSubject] = useState<string>("");
  const [isEditingEnabled, setIsEditingEnabled] = useState<boolean>(false);
  const navigate = useNavigate();
  const { handleClearUserDataFromStorage } = useAuth();

  const handleShowDeleteModal = async (subjectLinkName: string) => {
    setIsDeleteModalVisible(true);
    setSelectedSubject(subjectLinkName);
  };

  const handleDeleteSubject = async (event: FormEvent) => {
    event.preventDefault();
    try {
      await api.delete(`/subjects/${selectedSubject}`);
      toast.success("Deletado com sucesso!");
      setIsDeleteModalVisible(false);
    } catch (error) {
      handleNotifyError(error, navigate, handleClearUserDataFromStorage);
    } finally {
      onChange();
    }
  };

  const handleDeleteTopic = async () => {
    try {
      await api.delete(`/topics/${topic.linkName}`);
      setIsTopicBeingDeleted(false)
      onChange();
    } catch (error) {
      handleNotifyError(error, navigate, handleClearUserDataFromStorage);
    }
  };

  return (
    <Fragment key={topic.id}>
      <ConfirmationModal
        message="Deseja realmente excluir esse conteúdo?"
        confirmText="Deletar"
        cancelText="Cancelar"
        isVisible={isDeleteModalVisible}
        setIsVisible={setIsDeleteModalVisible}
        onConfirm={
          isTopicBeingDeleted ? handleDeleteTopic : handleDeleteSubject
        }
      />
      <AddOrUpdateSection
        mode="update"
        isVisible={isEditingEnabled}
        currentCourseLinkName={params.coursename}
        setIsVisible={setIsEditingEnabled}
        setTopics={onChange}
        topic={topic}
      />
      <div className="topic-header">
        <h2 className="topic-title">{topic.name}</h2>
        <FiEdit
          color="var(--grey)"
          size={20}
          onClick={() => setIsEditingEnabled(true)}
        />
        <FiTrash2
          color="var(--grey)"
          size={20}
          onClick={() => {
            setIsTopicBeingDeleted(true);
            setIsDeleteModalVisible(true);
          }}
        />
      </div>
      {enableAdminMode ? (
        <>
          <div className="trails-grid-container">
            {topic.subjects.sort(reorderSubjects).map((subject, index) => (
              <Draggable
                draggableId={String(subject.id)}
                index={index}
                key={subject.id}
              >
                {(provided) => (
                  <div
                    className="draggable-wrapper"
                    ref={provided.innerRef}
                    {...provided.draggableProps}
                    {...provided.dragHandleProps}
                    /* the id to be used to find this DOM element and add the custom placeholder while dragging*/
                    id={`draggable-${subject.id}`}
                  >
                    <Subject
                      showOptions={enableAdminMode}
                      coursename={params.coursename as string}
                      topicId={topic.id}
                      subject={subject}
                      onDelete={handleShowDeleteModal}
                    />
                  </div>
                )}
              </Draggable>
            ))}
          </div>
          <PlusButton
            text="Novo conteúdo"
            color="var(--dark-green)"
            topicId={topic.id}
            coursename={params.coursename as unknown as number}
          />
        </>
      ) : (
        <div className="trails-grid-container">
          {topic.subjects.map((subject) => (
            <Subject
              key={subject.id}
              showOptions={enableAdminMode}
              coursename={params.coursename as string}
              topicId={topic.id}
              subject={subject}
              onDelete={handleShowDeleteModal}
            />
          ))}
        </div>
      )}
    </Fragment>
  );
}
