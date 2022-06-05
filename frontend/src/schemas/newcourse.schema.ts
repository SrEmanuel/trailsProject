import * as Yup from "yup";

export const NewCourseSchema = Yup.object().shape({
 name: Yup.string().required('O titulo é um campo obrigatório'),
 professorID: Yup.string().required('O professor é um campo obrigatório'),
 image:Yup.mixed().required('A imagem é um campo obrigatório')
});

export const UpdateCourseSchema = Yup.object().shape({
  name: Yup.string().required('O titulo é um campo obrigatório'),
  professorID: Yup.string(),
  image:Yup.mixed().required('A imagem é um campo obrigatório')
})
