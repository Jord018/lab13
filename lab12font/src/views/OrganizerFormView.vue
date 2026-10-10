<script setup lang="ts">
import type { Organizer } from '@/types'
import { ref } from 'vue'
import OrganizerService from '@/services/OrganizerService'
import { useRouter } from 'vue-router'
import { useMessageStore } from '@/stores/message'
import ImageUpload from '@/components/ImageUpload.vue'

const organizer = ref<Omit<Organizer, 'id'>>({
  name: '',
  address: '',
})
// only 1 image is allowed for an organizer
const images = ref<string[]>([])

const router = useRouter()
const store = useMessageStore()

function saveOrganizer() {
  OrganizerService.saveOrganizer({ ...organizer.value, image: images.value[0] })
    .then((response) => {
      router.push({ name: 'organizer-detail-view', params: { id: response.data.id } })
      store.updateMessage(
        'You are successfully added a new organizer: ' + response.data.name,
      )
      setTimeout(() => {
        store.resetMessage()
      }, 3000)
    })
    .catch(() => {
      router.push({ name: 'network-error-view' })
    })
}
</script>

<template>
  <div>
    <h1>Create an Organizer</h1>
    <form @submit.prevent="saveOrganizer">
      <h3>Organization Info</h3>
      <label class="block text-gray-500 font-bold">Organization Name</label>
      <input
        v-model="organizer.name"
        type="text"
        placeholder="Organization Name"
        class="h-13 w-full px-2.5 text-xl border border-gray-400 focus:border-emerald-500 focus:outline-none mb-6"
      />

      <label class="block text-gray-500 font-bold">Address</label>
      <input
        v-model="organizer.address"
        type="text"
        placeholder="Address"
        class="h-13 w-full px-2.5 text-xl border border-gray-400 focus:border-emerald-500 focus:outline-none mb-6"
      />

      <h3>The image of the Organizer</h3>
      <ImageUpload v-model="images" :max="1" />

      <button
        class="flex w-fit mx-auto items-center justify-center h-13 px-10 rounded-md font-semibold whitespace-nowrap border border-gray-400 focus:border-emerald-500 transition-all duration-200 ease-linear hover:scale-105 hover:border-emerald-500 hover:shadow-lg active:scale-100 focus:outline-none"
        type="submit"
      >
        Submit
      </button>
    </form>

    <pre v-if="organizer.name || organizer.address">{{ { ...organizer, image: images[0] } }}</pre>
  </div>
</template>
