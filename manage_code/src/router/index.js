	import {
		createRouter,
		createWebHashHistory
	} from 'vue-router'
	import forum from '@/views/forum/list'
	import news from '@/views/news/list'
	import jiankangmubiao from '@/views/jiankangmubiao/list'
	import yundongjilu from '@/views/yundongjilu/list'
	import genzongjianyi from '@/views/genzongjianyi/list'
	import yonghu from '@/views/yonghu/list'
	import examquestion from '@/views/exam/examquestion/list'
	import jiankangtixing from '@/views/jiankangtixing/list'
	import exampaper from '@/views/exampaper/list'
	import examquestionbank from '@/views/exam/examquestionbank/list'
	import config from '@/views/config/list'
	import exampaperlist from '@/views/exam/exampaperlist/list'
	import examination from '@/views/exam/exampaperlist/examination'
	import examrecord from '@/views/exam/examrecord/list'
	import examfailrecord from '@/views/exam/examfailrecord/list'
	import usersCenter from '@/views/users/center'

export const routes = [{
		path: '/login',
		name: 'login',
		component: () => import('../views/login.vue')
	},{
		path: '/',
		name: '首页',
		component: () => import('../views/index'),
		children: [{
			path: '/',
			name: '首页',
			component: () => import('../views/HomeView.vue'),
			meta: {
				affix: true
			}
		}, {
			path: '/updatepassword',
			name: '修改密码',
			component: () => import('../views/updatepassword.vue')
		}
		
		,{
			path: '/usersCenter',
			name: '管理员个人中心',
			component: usersCenter
		}
		,{
			path: '/forum',
			name: '论坛交流',
			component: forum
		}
		,{
			path: '/news',
			name: '健康资讯',
			component: news
		}
		,{
			path: '/jiankangmubiao',
			name: '健康目标',
			component: jiankangmubiao
		}
		,{
			path: '/yundongjilu',
			name: '运动记录',
			component: yundongjilu
		}
		,{
			path: '/genzongjianyi',
			name: '跟踪建议',
			component: genzongjianyi
		}
		,{
			path: '/yonghu',
			name: '用户',
			component: yonghu
		}
		,{
			path: '/examquestion',
			name: '试题管理',
			component: examquestion
		}
		,{
			path: '/jiankangtixing',
			name: '健康提醒',
			component: jiankangtixing
		}
		,{
			path: '/exampaper',
			name: '健康小测',
			component: exampaper
		}
		,{
			path: '/examquestionbank',
			name: '试题库管理',
			component: examquestionbank
		}
		,{
			path: '/config',
			name: '轮播图',
			component: config
		}
		, {
			path: '/exampaperlist',
			name: '考试列表',
			component: exampaperlist
		}, {
			path: '/examrecord',
			name: '考试记录',
			component: examrecord
		}, {
			path: '/examfailrecord',
			name: '错题本',
			component: examfailrecord
		}
		]
	},
	{
		path: '/examination',
		name: '考试',
		component: examination
	},
]

const router = createRouter({
	history: createWebHashHistory(process.env.BASE_URL),
	routes
})

export default router
