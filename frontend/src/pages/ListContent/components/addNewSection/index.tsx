import { FiX } from "react-icons/fi";

import { ModalContainer } from "../../../../components/ModalContainer";
import { Overlay } from "../../../../components/Overlay";

import "./styles.scss";

interface Props {
  isVisible: boolean;
  setSection: React.Dispatch<React.SetStateAction<string | undefined>>;
}

export function AddNewSection({ isVisible, setSection }: Props) {
  return (
    <Overlay hidden={!isVisible}>
      <ModalContainer>
        <FiX size={24} color="var(--red)" />
        <h2>Deseja adicionar uma nova sessão?</h2>
        <input onChange={ (e) => setSection(e.target.value)} placeholder="Nome da sessão" />
        <div className="btn-container">
          <button>Cancelar</button>
          <button>Criar</button>
        </div>
      </ModalContainer>
    </Overlay>
  );
}
