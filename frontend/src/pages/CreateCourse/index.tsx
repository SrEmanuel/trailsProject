import { FiArrowLeft } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { Dropzone } from "../../components/Dropzone";
import { WaveContainer } from "../../components/WaveContainer";
import "./styles.scss";

export function CreateCourse() {

  const navigate = useNavigate();

  return (
    <WaveContainer>
      <span className="goBack" onClick={()=> navigate(-1)}>
        <FiArrowLeft size={24} color="var(--dark-green)" /> Voltar
      </span>
      <main className="create-course-container">
        <h1>Criar novo curso</h1>
        <form>
          <Dropzone />
          <input type="text" name="name" placeholder="Nome do curso" />
          <div className="buttons-container">
            <button>Cancelar</button>
            <button>Avançar</button>
          </div>
        </form>
      </main>
    </WaveContainer>
  );
}
