import * as Yup from "yup";

export const NameSchema = Yup.object().shape({
  value: Yup.string().min(3, 'Minimo de três caracteres').required('O nome é obrigatório')
});

export const EmailSchema = Yup.object().shape({
  value: Yup.string().email('Informe um e-mail válido').required('O e-mail é obrigatório')
});

export const UpdatePasswordSchema = Yup.object().shape({
  oldPassword: Yup.string().required('A senha é um campo obrigatório').matches(/(?=.*[a-z])(?=.*[A-Z])(?=.*\W)/, 'A senha deve conter: 1 letra maiúscula, 1 letra minúscula, 1 caracter especial, sem espaços').min(8, 'Minimo de 8 caracteres').max(64, 'máximo de 64 caracteres'),
  password: Yup.string().required('A senha é um campo obrigatório').matches(/(?=.*[a-z])(?=.*[A-Z])(?=.*\W)/, 'A senha deve conter: 1 letra maiúscula, 1 letra minúscula, 1 caracter especial, sem espaços').min(8, 'Minimo de 8 caracteres').max(64, 'máximo de 64 caracteres'),
  confirmPassword: Yup.string().oneOf([Yup.ref('password'), null], 'As senhas devem ser iguais').required('Confirme sua senha')
})