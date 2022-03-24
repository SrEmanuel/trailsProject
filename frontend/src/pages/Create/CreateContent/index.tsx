import { useState } from "react";
import { FiArrowLeft } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { CKEditor } from "ckeditor4-react";
import { Dropzone } from "../../../components/Dropzone";
import { WaveContainer } from "../../../components/WaveContainer";
import "../styles.scss";
import "./styles.scss";

export function CreateContent() {
  const [step, setStep] = useState(1);
  const navigate = useNavigate();

  return (
    <WaveContainer>
      <span className="goBack" onClick={() => navigate(-1)}>
        <FiArrowLeft size={24} color="var(--dark-green)" /> Voltar
      </span>
      <h1>Criar nova postagem</h1>
      <main className="create-container">
        {step === 1 ? (
          <form>
            <Dropzone />
            <input type="text" name="name" placeholder="Título" />
            <select placeholder="Selecione uma série">
              <option selected disabled hidden value="0">
                Selecione uma opção
              </option>
              <option value="1">1º ano</option>
              <option value="1">2º ano</option>
              <option value="1">3º ano</option>
            </select>
            <div className="buttons-container">
              <button>Cancelar</button>
              <button onClick={() => setStep(2)}>Avançar</button>
            </div>
          </form>
        ) : (
          <>
            <CKEditor
              style={{ width: "90%" }}
              initData={
                <p>This is an example CKEditor 4 WYSIWYG editor instance.</p>
              }
            />
            <div className="buttons-container">
              <button>Voltar</button>
              <button onClick={() => setStep(2)}>Concluir</button>
            </div>
          </>
        )}
      </main>
    </WaveContainer>
  );
}
