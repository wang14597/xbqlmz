import React from 'react';

interface CommonButtonProps {
  type: 'submit' | 'reset' | 'button' | undefined;
  value: string;
  showColor?: boolean;
}

export default function CommonButton({
                                       type,
                                       value,
                                       showColor = false,
                                     }: CommonButtonProps) {
  return (
    <button
      type={type}
      className={
      !showColor ?
        'w-full px-4 py-2 mt-4 font-medium text-indigo-600 border border-indigo-600 rounded hover:bg-indigo-100 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500'
        : 'w-full px-4 py-2 font-medium text-white bg-indigo-600 rounded hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500'}
    >
      {value}
    </button>
  );
}
