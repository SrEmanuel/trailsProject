import * as Yup from "yup";

export const PostSchema = Yup.object().shape({
  name: Yup.string().required("O titulo é um campo obrigatório"),
  grade: Yup.string().required("A śerie é um campo obrigatório"),
  image: Yup.mixed().required("A imagem é um campo obrigatório"),
  htmlContent: Yup.string().required("Um conteúdo é necessário"),
  QuestionHtmlContent: Yup.string().required("Um conteúdo é necessário"),
  correct: Yup.string().required("É necessário marcar uma opção como correta"),
  answerA: Yup.string().required("É necessario preencher essa alternativa"),
  answerB: Yup.string().required("É necessario preencher essa alternativa"),
  answerC: Yup.string().required("É necessario preencher essa alternativa"),
  answerD: Yup.string().required("É necessario preencher essa alternativa"),
  answerE: Yup.string().required("É necessario preencher essa alternativa"),
});
