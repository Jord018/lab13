<script setup lang="ts">
import type { Organizer } from '@/types'
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import OrganizerService from '@/services/OrganizerService'
import EventService from '@/services/EventService'

const props = defineProps<{
  id: string
}>()
const organizer = ref<Organizer | null>(null)
const imageUrl = ref<string>()
const router = useRouter()

onMounted(() => {
  OrganizerService.getOrganizer(parseInt(props.id))
    .then((response) => {
      organizer.value = response.data
      if (organizer.value?.image) {
        // reuse the presigned url lookup used for event images
        EventService.getEventImages([organizer.value.image]).then((urls) => {
          imageUrl.value = urls[0]
        })
      }
    })
    .catch((error) => {
      if (error.response && error.response.status === 404) {
        router.push({ name: '404-resource-view', params: { resource: 'organizer' } })
      } else {
        router.push({ name: 'network-error-view' })
      }
    })
})
</script>

<template>
  <div v-if="organizer">
    <h1>{{ organizer.name }}</h1>
    <p>{{ organizer.address }}</p>
    <div class="flex flex-row flex-wrap justify-center">
      <img v-if="imageUrl" :src="imageUrl" alt="organizer image"
        class="border-solid border-gray-200 border-2 rounded p-1 m-1 w-40 hover:shadow-lg" />
    </div>
    <h3>Events</h3>
    <ul>
      <li v-for="event in organizer.ownEvents" :key="event.id ?? undefined">
        <RouterLink :to="{ name: 'event-detail-view', params: { id: event.id } }">{{ event.title }}</RouterLink>
      </li>
    </ul>
  </div>
</template>
