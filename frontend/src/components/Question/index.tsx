import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../hooks/useAuth";
import { IQuestion } from "../../interfaces/question";
import api from "../../services/api";
import { handleNotifyError } from "../../utils/handleNotifyError";
import "./styles.scss";

interface Props {
  data: IQuestion;
  handleNextQuestion: (arg: number) => void;
  currentQuestion: number;
  totalQuestions: number;
}

export function Question({
  data,
  currentQuestion,
  totalQuestions,
  handleNextQuestion,
}: Props) {
  const [isAnswered, setIsAnswered] = useState<boolean>(false);
  const [selectedOption, setSelectedOption] = useState<string>("");
  const navigate = useNavigate();
  const { handleClearUserDataFromStorage } = useAuth();

  function nextQuestion() {
    if (currentQuestion < totalQuestions) {
      handleNextQuestion(currentQuestion + 1);
    }
  }

  async function handleSubmitAnswer() {
    try {
      const response = await api.get(`/questions/${data.id}/verifyAnswer"`);
      console.log(response.data);
      setIsAnswered(true);
    } catch (error) {
      handleNotifyError(error, navigate, handleClearUserDataFromStorage);
    }
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
        <div className="alternative">
          <input
            onClick={() => setSelectedOption(data.answerA)}
            type="radio"
            id={data.answerA}
            name="alternative"
          />
          <label htmlFor={data.answerA}>
            {data.answerA}
          </label>
        </div>
        <div className="alternative">
          <input
            onClick={() => setSelectedOption(data.answerB)}
            type="radio"
            id={data.answerB}
            name="alternative"
          />
          <label htmlFor={data.answerB}>
            {data.answerB}
          </label>
        </div>
        <div className="alternative">
          <input
            onClick={() => setSelectedOption(data.answerC)}
            type="radio"
            id={data.answerC}
            name="alternative"
          />
          <label htmlFor={data.answerC}>
            {data.answerC}
          </label>
        </div>
        <div className="alternative">
          <input
            onClick={() => setSelectedOption(data.answerD)}
            type="radio"
            id={data.answerD}
            name="alternative"
          />
          <label htmlFor={data.answerD}>
            {data.answerD}
          </label>
        </div>
        <div className="alternative">
          <input
            onClick={() => setSelectedOption(data.answerE)}
            type="radio"
            id={data.answerE}
            name="alternative"
          />
          <label htmlFor={data.answerE}>
            {data.answerE}
          </label>
        </div>
      </div>
      <div className="controls">
        <button disabled={!selectedOption} onClick={handleSubmitAnswer}>
          Enviar Resposta
        </button>
        <button
          disabled={!(currentQuestion < totalQuestions) || !isAnswered}
          onClick={nextQuestion}
        >
          Próximo
        </button>
      </div>
    </div>
  );
}
