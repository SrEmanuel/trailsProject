import * as Yup from "yup";

export const NewContentSchema = Yup.object().shape({
 title: Yup.string().required('O titulo é um campo obrigatório'),
 grade: Yup.string().required('A śerie é um campo obrigatório'),
 image:Yup.mixed().required('A imagem é um campo obrigatório')
});
