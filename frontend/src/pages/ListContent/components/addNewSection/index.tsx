import { FiX } from 'react-icons/fi';

import { ModalContainer } from "../../../../components/ModalContainer";
import { Overlay } from "../../../../components/Overlay";

import './styles.scss';

interface Props{
  isVisible: boolean;
}

export function AddNewSection({isVisible}: Props){
  return(
    <Overlay hidden={!isVisible} >
      <ModalContainer>
        <FiX size={24} color="var(--red)" />
        <h2>Deseja adicionar uma nova sessão?</h2>
        <input placeholder='Nome da sessão' />
        <div className="btn-container">
          <button>Cancelar</button>
          <button>Criar</button>
        </div>
      </ModalContainer>
    </Overlay>
  )
}