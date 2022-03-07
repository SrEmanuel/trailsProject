import * as Yup from "yup";

export const RegisterSchema = Yup.object().shape({
  name: Yup.string().required('O campo nome é obrigatório'),
  email: Yup.string().email('Por favor, informar um e-mail válido').required('O campo e-mail é obrigatório'),
  password: Yup.string().required('A senha é um campo obrigatório').matches(/(?=.*[a-z])(?=.*[A-Z])(?=.*\W)/, 'A senha deve conter: 1 letra maiúscula, 1 letra minúscula, 1 caracter especial, sem espaços').min(8, 'Minimo de 8 caracteres').max(64, 'máximo de 64 caracteres'),
  confirmPassword: Yup.string().oneOf([Yup.ref('password'), null], 'As senhas devem ser iguais').required('Confirme sua senha')
})