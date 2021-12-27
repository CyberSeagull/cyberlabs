<template>
  <div>
    <a-row>
      <a-col :span="8">
        <AppLoginForm></AppLoginForm>
      </a-col>
      <a-col :span="8">
        <AppRegisterForm></AppRegisterForm>
      </a-col>
    </a-row>

  </div>
</template>

<script>
export default {
  name: 'IndexPage',
}
</script>
