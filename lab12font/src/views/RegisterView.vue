<script setup lang="ts">
import InputText from '@/components/InputText.vue'
import * as yup from 'yup'
import { useField, useForm } from 'vee-validate'
import { useAuthStore } from '@/stores/auth'
import { useMessageStore } from '@/stores/message'
import { useRouter } from 'vue-router'
const authStore = useAuthStore()
const messageStore = useMessageStore()
const router = useRouter()
const validationSchema = yup.object({
  username: yup.string().required('The username is required'),
  firstname: yup.string().required('The first name is required'),
  lastname: yup.string().required('The last name is required'),
  email: yup.string().required('The email is required').email('Input must be an email.'),
  password: yup.string().required('The password is required').min(6, 'The password must be at least 6 characters.')
})
const { errors, handleSubmit } = useForm({
  validationSchema,
  initialValues: { username: '', firstname: '', lastname: '', email: '', password: '' }
})
const { value: username } = useField<string>('username')
const { value: firstname } = useField<string>('firstname')
const { value: lastname } = useField<string>('lastname')
const { value: email } = useField<string>('email')
const { value: password } = useField<string>('password')
const onSubmit = handleSubmit((values) => {
  authStore
    .register(values)
    .then(() => authStore.login(values.username, values.password))
    .then(() => {
      router.push({ name: 'event-list-view' })
    })
    .catch(() => {
      messageStore.updateMessage('could not register')
      setTimeout(() => {
        messageStore.resetMessage()
      }, 3000)
    })
})
</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <h2 class="text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Create your account</h2>
    </div>
    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <form class="space-y-6" @submit.prevent="onSubmit">
        <div>
          <label class="block text-sm font-medium leading-6 text-gray-900">Username</label>
          <InputText v-model="username" placeholder="Username" :error="errors['username']" />
        </div>
        <div>
          <label class="block text-sm font-medium leading-6 text-gray-900">First name</label>
          <InputText v-model="firstname" placeholder="First name" :error="errors['firstname']" />
        </div>
        <div>
          <label class="block text-sm font-medium leading-6 text-gray-900">Last name</label>
          <InputText v-model="lastname" placeholder="Last name" :error="errors['lastname']" />
        </div>
        <div>
          <label class="block text-sm font-medium leading-6 text-gray-900">Email address</label>
          <InputText v-model="email" placeholder="Email address" :error="errors['email']" />
        </div>
        <div>
          <label class="block text-sm font-medium leading-6 text-gray-900">Password</label>
          <InputText v-model="password" type="password" placeholder="Password" :error="errors['password']" />
        </div>
        <div>
          <button type="submit" class="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500">Sign up</button>
        </div>
      </form>
    </div>
  </div>
</template>
