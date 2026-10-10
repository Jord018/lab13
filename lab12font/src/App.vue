<script setup lang="ts">
import { RouterLink, RouterView, useRouter } from 'vue-router'
import { useMessageStore } from '@/stores/message'
import { useAuthStore } from './stores/auth'
import { storeToRefs } from 'pinia'
import SvgIcon from '@jamescoyle/vue-icon'
import { mdiAccount, mdiAccountPlus, mdiLogin, mdiLogout } from '@mdi/js'
const store = useMessageStore()
const authStore = useAuthStore()
const router = useRouter()
const { message } = storeToRefs(store)

function logout() {
  authStore.logout()
  router.push({ name: 'login-view' })
}
</script>

<template>
  <div class="text-center text-grey-700 antialiased">
    <header>
      <div id="flashMessage" class="animate-fade" v-if="message">
        <h4>
          {{ message }}
        </h4>
      </div>
      <div class="wrapper">
        <nav class="py-6">
          <nav class="flex">
            <ul v-if="!authStore.currentUserName" class="flex navbar-nav ml-auto">
              <li class="nav-item px-2">
                <router-link to="/register" class="nav-link">
                  <div class="flex items-center">
                    <SvgIcon type="mdi" :path="mdiAccountPlus" />
                    <span class="ml-3">Sign Up</span>
                  </div>
                </router-link>
              </li>
              <li class="nav-item px-2">
                <router-link to="/login" class="nav-link">
                  <div class="flex items-center">
                    <SvgIcon type="mdi" :path="mdiLogin" />
                    <span class="ml-3">Login</span>
                  </div>
                </router-link>
              </li>
            </ul>
            <ul v-if="authStore.currentUserName" class="flex navbar-nav ml-auto">
              <li class="nav-item px-2">
                <router-link to="/profile" class="nav-link">
                  <div class="flex items-center">
                    <SvgIcon type="mdi" :path="mdiAccount" />
                    <span class="ml-3">{{ authStore.currentUserName }}</span>
                  </div>
                </router-link>
              </li>
              <li class="nav-item px-2">
                <a class="nav-link hover:cursor-pointer" @click="logout">
                  <div class="flex items-center">
                    <SvgIcon type="mdi" :path="mdiLogout" />
                    <span class="ml-3">LogOut</span>
                  </div>
                </a>
              </li>
            </ul>
          </nav>
          <RouterLink class="font-bold text-gray-700" exact-active-class="text-green-500" :to="{ name: 'event-list-view' }">Event</RouterLink> |
          <RouterLink class="font-bold text-gray-700" exact-active-class="text-green-500" :to="{ name: 'organizer-list-view' }">Organizer</RouterLink> |
          <RouterLink class="font-bold text-gray-700" exact-active-class="text-green-500" :to="{ name: 'about' }">About</RouterLink> |
          <RouterLink class="font-bold text-gray-700" exact-active-class="text-green-500" :to="{ name: 'add-event' }">New Event</RouterLink> |
          <RouterLink class="font-bold text-gray-700" exact-active-class="text-green-500" :to="{ name: 'add-organizer' }">Add Organizer</RouterLink>
        </nav>
      </div>
    </header>

    <RouterView />
  </div>
</template>

<style>
h2 {
  font-size: 20px;
}
</style>
