import { ChangeEvent, useState, createRef, } from "react";
import { FiUploadCloud, FiX } from "react-icons/fi";
import "./styles.scss";

export function Dropzone() {
  const [selectedFile, setSelectedFile] = useState<File>();
  const [selectedFilePreview, setSelectedFilePreview] = useState<string>();
  const inputRef = createRef<HTMLInputElement>();

  function handleFileUpload(e: ChangeEvent<HTMLInputElement>) {
    if (e.target.files) {
      setSelectedFile(e.target.files[0]);
      setSelectedFilePreview(URL.createObjectURL(e.target.files[0]));
      console.log(selectedFilePreview? true: false);
    }
  }

  function handleClearFileSelection(){
    setSelectedFile(undefined);
    setSelectedFilePreview(undefined);
    (inputRef.current as any ).value = '';
  }

  return (
    <div className="dropzone-container">
      {selectedFilePreview ? (
        <div className="dropzone-preview">
          <FiX size={24} color="var(--red)" onClick={handleClearFileSelection} />
          <img src={selectedFilePreview} alt={selectedFile?.name} />
        </div>
      ) : (
        <label htmlFor="dropzone">
          <FiUploadCloud color="var(--dark-green)" size={24} />
          Arraste ou clique para adicionar uma imagem (opcional)
        </label>
      )}
      <input
        onChange={(e) => handleFileUpload(e)}
        name="dropzone"
        ref={inputRef}
        id="dropzone"
        type="file"
      />
    </div>
  );
}
