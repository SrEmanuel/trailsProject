import { FormEvent, Fragment, useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { useAuth } from "../../hooks/useAuth";
import { ITopic } from "../../interfaces/topic";
import api from "../../services/api";
import { handleNotifyError } from "../../utils/handleNotifyError";
import { DeleteSubject } from "../DeleteSubject";
import { PlusButton } from "../PlusButton";
import { Subject } from "../Subject";

interface Props {
  topic: ITopic;
  params: any;
  enableAdminMode: boolean;
  onDeleteSubject: () => void;
}

export function Topic({ topic, params, enableAdminMode, onDeleteSubject }: Props) {
  const [isDeleteModalVisible, setIsDeleteModalVisible] = useState(false);
  const [selectedSubject, setSelectedSubject] = useState<string>("");
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
      onDeleteSubject();
    }
  };

  return (
    <Fragment key={topic.id}>
      <DeleteSubject
        isVisible={isDeleteModalVisible}
        setIsVisible={setIsDeleteModalVisible}
        onDelete={handleDeleteSubject}
      />
      <h2 className="topic-title">{topic.name}</h2>
      <div className="trails-grid-container">
        {topic.subjects.map((subject) => (
          <Subject
            showOptions={enableAdminMode}
            key={subject.id}
            coursename={params.coursename as string}
            topicId={topic.id}
            subject={subject}
            onDelete={handleShowDeleteModal}
          />
        ))}
        {enableAdminMode && (
          <PlusButton
            text="Novo conteúdo"
            color="var(--dark-green)"
            topicId={topic.id}
            coursename={params.coursename as unknown as number}
          />
        )}
      </div>
    </Fragment>
  );
}
