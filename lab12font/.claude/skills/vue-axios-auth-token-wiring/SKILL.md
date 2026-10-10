---
name: vue-axios-auth-token-wiring
description: Use when adding an auth token to axios in Vue.
category: frontend
---
Use one shared axios instance per backend and attach the auth header in one interceptor. Service modules never call `axios.create` or set auth themselves.

- `src/services/AxiosClient.ts` creates the single instance (`baseURL` from `import.meta.env.VITE_BACKEND_URL`, JSON headers) and default-exports it. Services import it.
- `src/services/AxiosInterceptorSetup.ts` adds a request interceptor that reads `access_token` from `localStorage` and sets `Authorization: Bearer <token>` when present. Import it once for side effects in `src/main.ts`.
- The auth store writes the token with `localStorage.setItem('access_token', ...)` right after a successful login. Redirect only in the `.then` after that write.
- Do not log the token. Drop any `console.log('token', ...)` copied from a spec.
- The login request itself needs no header; leave its axios usage alone if it runs before a token exists.
- Check with `npx vue-tsc --noEmit`, then verify in the browser per `vue-spa-login-playwright-verification`.
