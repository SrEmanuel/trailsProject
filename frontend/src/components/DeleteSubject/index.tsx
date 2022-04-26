import { FormEvent } from "react";
import { ModalContainer } from "../ModalContainer";
import { Overlay } from "../Overlay";

interface Props {
  isVisible: boolean;
  setIsVisible: (arg: boolean) => void;
  onDelete: (event: FormEvent) => void;
}

export function DeleteSubject({isVisible, setIsVisible, onDelete }: Props) {
  return (
    <Overlay hidden={!isVisible}>
      <ModalContainer>
        <h2>Deseja realmente excluir este conteúdo?</h2>
        <div className="btn-container">
          <button type="button" onClick={() => setIsVisible(false)}>
            Cancelar
          </button>
          <button type="submit" onClick={onDelete} >
            Deletar
          </button>
        </div>
      </ModalContainer>
    </Overlay>
  );
}
