<script setup lang="ts">
import { useOrganizerStore } from '@/stores/organizer'
import { storeToRefs } from 'pinia'

const store = useOrganizerStore()
const { organizer } = storeToRefs(store)
</script>
<template>
  <div v-if="organizer">
    <h1>{{ organizer.name }}</h1>
    <nav>
      <RouterLink :to="{ name: 'organizer-detail-view' }">Details</RouterLink>
    </nav>
    <RouterView :organizer="organizer" />
  </div>
</template>
