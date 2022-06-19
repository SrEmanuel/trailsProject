import * as Yup from "yup";

export const NewCourseSchema = Yup.object().shape({
 name: Yup.string().required('O titulo é um campo obrigatório'),
 rofessors: Yup.array().min(1, 'É necessário ao menos um professor').required('É necessário ao menos um professor'),
 image:Yup.mixed().required('A imagem é um campo obrigatório')
});

export const UpdateCourseSchema = Yup.object().shape({
  name: Yup.string().required('O titulo é um campo obrigatório'),
  professors: Yup.array().min(1, 'É necessário ao menos um professor').required('É necessário ao menos um professor'),
  image:Yup.mixed().required('A imagem é um campo obrigatório')
})
