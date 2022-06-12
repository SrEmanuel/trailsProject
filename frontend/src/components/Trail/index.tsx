import "./styles.scss";
import { FiEdit, FiTrash2 } from "react-icons/fi";
import { ITrails } from "../../interfaces/Trail";
import { useNavigate } from "react-router-dom";
import { FormEvent, useState } from "react";
import { ConfirmationModal } from "../ConfirmationModal";
import api from "../../services/api";
import { toast } from "react-toastify";
import { handleNotifyError } from "../../utils/handleNotifyError";
import { useAuth } from "../../hooks/useAuth";

interface Props {
  color?: string;
  trail: ITrails;
  mode: "teacher" | "student" | "guest" | "admin";
  onChange?: (...args: any) => void;
}

export function Trail({ color, trail, mode, onChange }: Props) {
  const [isDeleteModalVisible, setIsDeleteModalVisible] = useState(false);
  const { handleClearUserDataFromStorage } = useAuth();
  const navigate = useNavigate();

  const percent: number = trail.completedCount
    ? Math.round((trail.completedCount * 100) / trail.subjectsCount)
    : 0;

  function navigateToUpdateCourse(e: FormEvent) {
    e.stopPropagation();
    navigate(`/cursos/atualizar/${trail.linkName}`);
  }

  function handleShowModal(e: FormEvent) {
    setIsDeleteModalVisible(true);
  }

  async function handleDeleteCourse() {
    try {
      await api.delete(`/courses/${trail.linkName}`);
      toast.success("Deletado com sucesso");
      setIsDeleteModalVisible(false);
      onChange && onChange();
    } catch (error) {
      if ((error as any).data.status === 400) {
        toast.error(
          "Não é possível excluir esse curso, pois ele não está vazio"
        );
      } else {
        handleNotifyError(error, navigate, handleClearUserDataFromStorage);
      }
    }
  }

  return (
    <div className="trail-container" style={{ background: color }}>
      <ConfirmationModal
        message="Deseja realmente excluir esse conteúdo?"
        confirmText="Deletar"
        cancelText="cancelar"
        isVisible={isDeleteModalVisible}
        setIsVisible={setIsDeleteModalVisible}
        onConfirm={handleDeleteCourse}
      />
      <img
        onClick={() => navigate(`/cursos/${trail.linkName}`)}
        src={trail.imagePath}
        alt="capa do card"
      />
      <div className="card-line"></div>
      <div
        className={`trail-bottom ${
          mode !== "student" && "smaller-card-bottom"
        }`}
      >
        <div className="title-and-options">
          <h3>{trail.name}</h3>
          {mode === "admin" && (
            <div className="options">
              <FiEdit
                onClick={navigateToUpdateCourse}
                size={20}
                color="var(--grey)"
              />
              <FiTrash2
                onClick={handleShowModal}
                size={20}
                color="var(--grey)"
              />
            </div>
          )}
          {mode === "teacher" && (
            <div style={{ width: 24 }} className="options">
              <FiEdit
                onClick={navigateToUpdateCourse}
                size={20}
                color="var(--grey)"
              />
            </div>
          )}
        </div>
        {/*mode !== "teacher" && <span>Professor - João Fernandes de Souza</span>*/}
        {mode === "student" && (
          <>
            <div className="progress-bar">
              <div
                style={{ width: `${percent}%` }}
                className="current-progress"
              ></div>
            </div>
            <span className="progress-label">{percent}% concluido</span>
          </>
        )}
      </div>
    </div>
  );
}
