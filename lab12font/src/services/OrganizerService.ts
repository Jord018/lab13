import apiClient from './AxiosClient'
import type { Organizer } from '@/types'

export default {
  saveOrganizer(organizer: Omit<Organizer, 'id'>) {
    return apiClient.post('/organizers', organizer)
  },
  getOrganizers() {
    return apiClient.get('/organizers')
  },
  getOrganizersPage(perPage: number, page: number) {
    return apiClient.get('/organizers?_limit=' + perPage + '&_page=' + page)
  },
  getOrganizer(id: number) {
    return apiClient.get('/organizers/' + id)
  }
}
