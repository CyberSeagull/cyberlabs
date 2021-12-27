<template>
  <div>

    <a-form layout="inline" :form="form" @submit="handleSubmit">
      <a-form-item :validate-status="userNameError() ? 'error' : ''" :help="userNameError() || ''">
        <a-input
          v-decorator="[
          'email',
          { rules: [{ required: true, message: 'Please input your email!' }] },
        ]"
          placeholder="Email"
        >
          <a-icon slot="prefix" type="user" style="color:rgba(0,0,0,.25)"/>
        </a-input>
      </a-form-item>
      <a-form-item :validate-status="passwordError() ? 'error' : ''" :help="passwordError() || ''">
        <a-input
          v-decorator="[
          'password',
          { rules: [{ required: true, message: 'Please input your Password!' }] },
        ]"
          type="password"
          placeholder="Password"
        >
          <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)"/>
        </a-input>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit" :disabled="hasErrors(form.getFieldsError())">
          Log in
        </a-button>
      </a-form-item>
    </a-form>
    <a-modal v-if="user" v-model="visible" title="Current User">
      <p> {{ user._id }}</p>
      <p> {{ user.email }}</p>
      <p> {{ user.nickname }}</p>
      <p> {{ user.phone }}</p>
    </a-modal>
  </div>
</template>

<script>
function hasErrors(fieldsError) {
  return Object.keys(fieldsError).some(field => fieldsError[field]);
}

export default {
  name: 'AppLoginForm',

  data() {
    return {
      user: null,
      visible: false,
      hasErrors,
      form: this.$form.createForm(this, { name: 'horizontal_login' }),
    };
  },
  mounted() {
    this.$nextTick(() => {
      // To disabled submit button at the beginning.
      this.form.validateFields();
    });
  },
  methods: {
    // Only show error after a field is touched.
    userNameError() {
      const { getFieldError, isFieldTouched } = this.form;
      return isFieldTouched('userName') && getFieldError('userName');
    },
    // Only show error after a field is touched.
    passwordError() {
      const { getFieldError, isFieldTouched } = this.form;
      return isFieldTouched('password') && getFieldError('password');
    },
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields(async (err, values) => {
        if (!err) {
          console.log('Received values of form: ', values);
          const result = await this.$axios.$post('/api/auth/login', values);
          if (result) {
            this.user = result.user;
            this.visible = true;
          }
        }
      });
    },
  },
};
</script>
