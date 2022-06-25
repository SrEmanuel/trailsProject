import { useState } from "react";
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
  handleNextQuestion: (arg:number) => void;
  currentQuestion: number;
  totalQuestions: number;
}

export function Question({ data, currentQuestion, totalQuestions, handleNextQuestion }: Props) {
  const [isAnswered, setIsAnswered] = useState<boolean>(false);
  const [selectedOption, setSelectedOption] = useState<string>('');

  function nextQuestion(){
    if(currentQuestion < totalQuestions){
      handleNextQuestion(currentQuestion + 1)
    }
  }

  function handleSubmitAnswer(){
    setIsAnswered(true);

  }

  return (
    <div className="question-container">
      <div className="line"></div>
      <h2>Exercício</h2>
      <div
        className="html-content"
        dangerouslySetInnerHTML={{ __html: data?.htmlContent as string }}
      ></div>
      <div className="alternatives">
        {data.alternatives.map((alternative) => (
          <div key={alternative.id} className="alternative">
            <input onClick={ () => setSelectedOption(alternative.text) } type="radio" id={alternative.id as unknown as string } name="alternative" />
            <label htmlFor={alternative.id as unknown as string}>{alternative.text}</label>
          </div>
        ))}
      </div>
      <div className="controls">
        <button disabled={!selectedOption} onClick={handleSubmitAnswer} >Enviar Resposta</button>
        <button disabled={ !(currentQuestion < totalQuestions ) || !isAnswered } onClick={nextQuestion} >Próximo</button>
      </div>  
    </div>
  );
}
