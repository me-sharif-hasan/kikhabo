import  login_datasource  from '../login/datasource/login_datasource.js';

const test_login= new login_datasource();
console.log(await test_login.doLogin("me.sharif.hasan@gmail.com", "12345678"));
console.log(await test_login.doLogin("me.hasa@gmail.com", "12345678"));


