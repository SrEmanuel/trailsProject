import { ChangeEvent, useState, createRef, useEffect } from "react";
import { FiUploadCloud, FiX } from "react-icons/fi";
import "./styles.scss";

interface Props {
  onChange?: (...args: any) => any;
  preview?: any;
}

export function Dropzone({ onChange, preview }: Props) {
  const [selectedFile, setSelectedFile] = useState<File>();
  const [selectedFilePreview, setSelectedFilePreview] = useState<string>();
  const inputRef = createRef<HTMLInputElement>();

  function handleFileUpload(e: ChangeEvent<HTMLInputElement>) {
    if (e.target.files) {
      onChange && onChange(e);
      setSelectedFile(e.target.files[0]);
      setSelectedFilePreview(URL.createObjectURL(e.target.files[0]));
    }
  }

  function handleClearFileSelection() {
    setSelectedFile(undefined);
    setSelectedFilePreview(undefined);
    (inputRef.current as any).value = "";
  }

  useEffect(()=> {
    if(preview){
      setSelectedFilePreview(preview);
    }
  }, [preview])

  return (
    <div className="dropzone-container">
      {selectedFilePreview ? (
        <div className="dropzone-preview">
          <FiX
            size={24}
            color="var(--red)"
            onClick={handleClearFileSelection}
          />
          <img src={selectedFilePreview} alt={selectedFile?.name} />
        </div>
      ) : (
        <label htmlFor="dropzone">
          <FiUploadCloud color="var(--dark-green)" size={24} />
          Arraste ou clique para adicionar uma imagem
        </label>
      )}
      <input
        onChange={(e) => handleFileUpload(e)}
        name="image"
        ref={inputRef}
        id="dropzone"
        type="file"
        accept="image/*"
      />
    </div>
  );
}
