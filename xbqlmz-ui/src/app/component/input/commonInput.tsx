import React, { HTMLInputTypeAttribute } from 'react';

interface CommonInputProps {
  type: HTMLInputTypeAttribute | undefined;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  placeholder: string;
  required: boolean;
}

export default function CommonInput(props: CommonInputProps) {
  return (
    <input
      type={props.type}
      placeholder={props.placeholder}
      value={props.value}
      onChange={props.onChange}
      className='w-full px-3 py-2 border rounded shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500'
      required={props.required}
    />
  );
}