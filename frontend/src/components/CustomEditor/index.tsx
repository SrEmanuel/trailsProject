import { Editor } from "@tinymce/tinymce-react";
import { useRef } from "react";


export function CustomEditor() {
  const editorRef = useRef() as any;
  return (
    <div className="editor">
      <Editor
        onInit={(evt, editor) => (editorRef.current = editor)}
        init={{
          height: 500,
          menubar: true,
          plugins: [
            "advlist autolink lists link image charmap print preview anchor",
            "searchreplace visualblocks code fullscreen",
            "insertdatetime media image table paste code help wordcount",
          ],
          toolbar:
            "undo redo | formatselect | " +
            "bold italic backcolor | alignleft aligncenter " +
            "alignright alignjustify | bullist numlist outdent indent | " +
            "removeformat | image | media | help",
          content_style:
            "body { font-family:Helvetica,Arial,sans-serif; font-size:14px;}",
        }}
      />
    </div>
  );
}
