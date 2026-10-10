<script setup lang="ts">
import type { Organizer } from '@/types'
defineProps<{
  organizer: Organizer
}>()
</script>

<template>
  <RouterLink class="organizer-link" :to="{ name: 'organizer-detail-view', params: { id: organizer.id } }">
    <div
      class="p-5 w-[250px] cursor-pointer border border-[#39495c] mb-[18px] hover:scale-101 hover:shadow-sp"
    >
      <h2>{{ organizer.name }}</h2>
      <span v-if="organizer.address">@ {{ organizer.address }}</span>
    </div>
  </RouterLink>
</template>

<style scoped>
.organizer-link {
  text-decoration: none;
  color: #2c3e50;
}
</style>
