import { FiArrowLeft } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { Dropzone } from "../../../components/Dropzone";
import { WaveContainer } from "../../../components/WaveContainer";
import "../styles.scss";

export function CreateContent() {

  const navigate = useNavigate();

  return (
    <WaveContainer>
      <span className="goBack" onClick={()=> navigate(-1)}>
        <FiArrowLeft size={24} color="var(--dark-green)" /> Voltar
      </span>
      <main className="create-container">
        <h1>Criar nova postagem</h1>
        <form>
          <Dropzone />
          <input type="text" name="name" placeholder="Título" />
          <select placeholder="Selecione uma série">
          <option selected disabled hidden value="0">Selecione uma opção</option>
            <option value="1">1º ano</option>
            <option value="1">2º ano</option>
            <option value="1">3º ano</option>
          </select>
          <div className="buttons-container">
            <button>Cancelar</button>
            <button>Avançar</button>
          </div>
        </form>
      </main>
    </WaveContainer>
  );
}
