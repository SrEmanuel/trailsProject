import "./styles.scss";

interface Props {
  label: string;
  value: string;
  onSubmit: (value: string) => void;
}

export function Editable({ label, value, onSubmit }: Props) {
  return <div>edit</div>;
}
