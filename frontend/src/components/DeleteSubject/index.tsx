import { FormEvent } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { useAuth } from "../../hooks/useAuth";
import api from "../../services/api";
import { handleNotifyError } from "../../utils/handleNotifyError";
import { ModalContainer } from "../ModalContainer";
import { Overlay } from "../Overlay";

interface Props {
  selectedSubjectLinkName: string;
  isVisible: boolean;
  setIsVisible: (arg: boolean) => void;
}

export function DeleteSubject({ selectedSubjectLinkName, isVisible, setIsVisible }: Props) {

  const { handleClearUserDataFromStorage } = useAuth();
  const navigate = useNavigate();

  async function handleDeleteSubject(event: FormEvent){
    event.preventDefault();
    try {
      await api.delete(`/subjects/${selectedSubjectLinkName}`);
      toast.success('Deletado com sucesso!');
      setIsVisible(false)
    } catch (error) {
      handleNotifyError(error, navigate, handleClearUserDataFromStorage);
    }
  }

  return (
    <Overlay hidden={!isVisible}>
      <ModalContainer>
        <h2>Deseja realmente excluir este conteúdo?</h2>
        <div className="btn-container">
          <button type="button" onClick={() => setIsVisible(false)}>
            Cancelar
          </button>
          <button type="submit" onClick={handleDeleteSubject} >
            Deletar
          </button>
        </div>
      </ModalContainer>
    </Overlay>
  );
}
