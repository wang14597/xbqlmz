'use client';
import React, { useState } from 'react';
import CommonButton from '@/app/component/button/commonButton';
import CommonInput from '@/app/component/input/commonInput';


export default function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = (event: React.FormEvent) => {
    event.preventDefault();
    console.log('Login:', { username, password });
  }

  return (
    <div className='flex items-center justify-center min-h-screen bg-gray-100'>
      <div className='w-full max-w-md p-8 space-y-4 bg-white rounded shadow-md'>
        <h1 className='text-2xl font-bold text-center'>Login</h1>
        <form onSubmit={handleLogin} className='space-y-4'>
          <CommonInput type={'text'}
                       value={username}
                       onChange={(e) => setUsername(e.target.value)}
                       placeholder={'Username'}
                       required={true} />

          <CommonInput type={'password'}
                       value={password}
                       onChange={(e) => setPassword(e.target.value)}
                       placeholder={'Password'}
                       required={true} />
        <CommonButton type={'submit'} value={'Login'} showColor={true} />
        </form>
        <CommonButton type={'submit'} value={'Register'} />
      </div>
    </div>
  );
};

