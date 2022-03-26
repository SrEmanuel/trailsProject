import * as Yup from "yup";

export const HtmlContentSchema = Yup.object().shape({
 htmlContent: Yup.string().required('O conteúdo da postagem é orbigatório')
});
