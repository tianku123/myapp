export default [
  // user
  {
    path: '/user',
    component: '../layouts/UserLayout',
    routes: [
      { path: '/user', redirect: '/user/login' },
      { path: '/user/login', component: './User/Login' },
      // { path: '/user/register', component: './User/Register' },
      // { path: '/user/register-result', component: './User/RegisterResult' },
    ],
  },
  // app
  {
    path: '/',
    component: '../layouts/BasicLayout',
    Routes: ['src/pages/Authorized'],
    authority: ['admin', 'user'],
    // authority: ['user'],
    routes: [
      // dashboard
      { path: '/', redirect: '/dashboard/fangzhu' },
      {
        path: '/dashboard',
        name: 'fangzhu',
        icon: 'user',
        routes: [
          {
            path: '/dashboard/fangzhu',
            name: 'fangzhu',
            component: './List/fangzhu/FangZhuList',
          },
        ],
      },
      {
        path: '/youke',
        name: 'youke',
        icon: 'table',
        routes: [
          {
            path: '/youke/youke',
            name: 'youke',
            component: './List/youke/YoukeList',
          },
        ],
      },
      {
        path: '/config',
        name: 'config',
        icon: 'setting',
        routes: [
          {
            path: '/config/jp',
            name: 'jp',
            component: './List/config/jpList',
          },
        ],
      },
      {
        component: '404',
      },
    ],
  },
];
