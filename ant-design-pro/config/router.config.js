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
        icon: 'dashboard',
        routes: [
          {
            path: '/dashboard/fangzhu',
            name: 'fangzhu',
            component: './List/fangzhu/FangZhuList',
          },
          // {
          //   path: '/dashboard/jprank',
          //   name: 'jprank',
          //   component: './List/rank/JpRankList',
          // },
          // {
          //   path: '/dashboard/ykrank',
          //   name: 'ykrank',
          //   component: './List/fangzhu/FangZhuList',
          // },
        ],
      },
      {
        path: '/youke',
        name: 'youke',
        icon: 'dashboard',
        routes: [
          {
            path: '/youke/youke',
            name: 'youke',
            component: './List/youke/YoukeList',
          },
        ],
      },
      {
        component: '404',
      },
    ],
  },
];
