import "./styles.scss";

interface IAlternatives {
  text: string;
  correct: boolean;
  id: number;
}

interface IQuestion {
  htmlContent: string;
  alternatives: IAlternatives[];
}

interface Props {
  data: IQuestion;
}

export function Question({ data }: Props) {
  return (
    <div className="question-container">
      <div
        className="html-content"
        dangerouslySetInnerHTML={{ __html: data?.htmlContent as string }}
      ></div>
      <div className="alternatives">
        {data.alternatives.map((alternative) => (
          <div key={alternative.id} className="alternative">
            <input type="radio" id={alternative.id as unknown as string } name="alternative" />
            <label htmlFor={alternative.id as unknown as string}>{alternative.text}</label>
          </div>
        ))}
      </div>
      <div className="controls">
        <button>Enviar Resposta</button>
        <button>Próximo</button>
      </div>  
    </div>
  );
}
