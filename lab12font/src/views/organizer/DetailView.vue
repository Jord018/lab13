<script setup lang="ts">
import { ref, toRefs, watch } from 'vue'
import { type Organizer } from '@/types'
import EventService from '@/services/EventService'

const props = defineProps<{
  organizer: Organizer
}>()
const { organizer } = toRefs(props)
const imageUrls = ref<string[]>([])
watch(
  organizer,
  (newOrganizer) => {
    EventService.getEventImages(newOrganizer.image ? [newOrganizer.image] : []).then((urls) => {
      imageUrls.value = urls
    })
  },
  { immediate: true },
)
</script>
<template>
  <p>{{ organizer.name }} @ {{ organizer.address }}</p>
  <div class="flex flex-row flex-wrap justify-center">
    <img v-for="image in imageUrls" :key="image" :src="image" alt="organizer image"
      class="border-solid border-gray-200 border-2 rounded p-1 m-1 w-40 hover:shadow-lg" />
  </div>
</template>
