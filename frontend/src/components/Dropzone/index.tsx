import { FiUploadCloud } from "react-icons/fi";
import "./styles.scss";

export function Dropzone() {
  return (
    <div className="dropzone-container">
      <label htmlFor="dropzone">
        <FiUploadCloud color="var(--dark-green)" size={24} />
        Arraste ou clique para adicionar uma imagem (opcional)
      </label>
      <input name="dropzone" id="dropzone" type="file" />
    </div>
  );
}
