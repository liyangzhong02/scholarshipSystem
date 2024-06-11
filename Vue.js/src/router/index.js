import { createRouter, createWebHistory } from 'vue-router'
import Layout from "@/layout/Layout";
import login from "@/views/login";
import Person from "@/views/Person";
import Student from "@/views/Student";
import Rule from "@/views/Rule";
import Board from "@/views/Board";
import Submit from "@/views/Submit";
import Rank1 from "@/views/Rank1";
import Rank2 from "@/views/Rank2";
import Rank3 from "@/views/Rank3";
const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout, //显示的页面
    redirect:"/login",  //重定向 访问此path的时候自动转到/student
    children:[
      {
        path: '/login',
        name: 'login',
        component: login
      },
      {
        path: '/person',
        name: 'Person',
        component: Person
      },
      {
        path: '/student',
        name: 'Student',
        component: Student
      },
      {
        path: '/rule',
        name: 'Rule',
        component: Rule
      },
      {
        path: '/board',
        name: 'Board',
        component: Board
      },
      {
        path: '/submit',
        name: 'Submit',
        component: Submit
      },
      {
        path: '/rank1',
        name: 'Rank1',
        component: Rank1
      },
      {
        path: '/rank2',
        name: 'Rank2',
        component: Rank2
      },
      {
        path: '/rank3',
        name: 'Rank3',
        component: Rank3
      },
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: login
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
