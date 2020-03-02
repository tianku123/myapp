export default [
  // blog
  {
    path: '/blog',
    // component: '../layouts/blog/BlogLayout',
    component: '../layouts/blog/Center/BlogLayout',
    routes: [
      {
        path: '/blog',
        redirect: '/blog/articles',
      },
      {
        path: '/blog/articles',
        component: '../layouts/blog/Center/Articles',
      },
      {
        path: '/blog/applications',
        component: '../layouts/blog/Center/Applications',
      },
      {
        path: '/blog/projects',
        component: '../layouts/blog/Center/Projects',
      },
    ],
  },
  // user
  {
    path: '/user',
    component: '../layouts/UserLayout',
    routes: [
      { path: '/user', redirect: '/user/login' },
      { path: '/user/login', component: './User/Login' },
      { path: '/user/register', component: './User/Register' },
      { path: '/user/register-result', component: './User/RegisterResult' },
    ],
  },
  // app
  {
    path: '/',
    component: '../layouts/BasicLayout',
    Routes: ['src/pages/Authorized'],
    authority: ['admin', 'user'],
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
          {
            path: '/dashboard/jprank',
            name: 'jprank',
            component: './List/fangzhu/FangZhuList',
          },
          {
            path: '/dashboard/ykrank',
            name: 'ykrank',
            component: './List/fangzhu/FangZhuList',
          },
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
            component: './List/fangzhu/FangZhuList',
          },
        ],
      },
      {
        component: '404',
      },
    ],
  },
];
