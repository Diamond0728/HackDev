<template>
    <div id="app">
        <v-app style="background-color:#eee;">
            <!--<app-header
                class="app-shell-header"
                @click-menu="handleClickHeaderMenu"
                @click-back="handleClickHeaderBack">
            </app-header>
            <app-sidebar
                @hide-sidebar = "handleHideSidebar"
                @show-sidebar = "handleShowSidebar"
            >
            </app-sidebar>-->
            
            <v-navigation-drawer
                fixed
                v-model="drawer"
                app
            >
                <v-list>
                <v-list-tile @click="$router.push('/booklist')">
                    <v-list-tile-action>
                    <v-icon></v-icon>
                    </v-list-tile-action>
                    <v-list-tile-content>
                    <v-list-tile-title>书籍信息</v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>

                <v-list-tile @click="$router.push('/borrowhis')">
                    <v-list-tile-action>
                    <v-icon></v-icon>
                    </v-list-tile-action>
                    <v-list-tile-content>
                    <v-list-tile-title>借书记录</v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>

                <v-list-tile @click="$router.push('/reservehis')">
                    <v-list-tile-action>
                    <v-icon></v-icon>
                    </v-list-tile-action>
                    <v-list-tile-content>
                    <v-list-tile-title>预约记录</v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>

                <v-list-tile @click="$router.push('/borrow')" v-if="storeUser.roles.admin||storeUser.roles.officer">
                    <v-list-tile-action>
                    <v-icon></v-icon>
                    </v-list-tile-action>
                    <v-list-tile-content>
                    <v-list-tile-title>借书管理</v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>
                <v-list-tile @click="$router.push('/return')" v-if="storeUser.roles.admin||storeUser.roles.officer">
                    <v-list-tile-action>
                    <v-icon></v-icon>
                    </v-list-tile-action>
                    <v-list-tile-content>
                    <v-list-tile-title>还书管理</v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>
                
                <v-list-group v-if="storeUser.roles.admin">
                    <v-list-tile slot="activator">
                        <v-list-tile-action>
                        <v-icon>home</v-icon>
                        </v-list-tile-action>
                        <v-list-tile-content>
                        <v-list-tile-title>用户管理</v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>
                    <v-list-tile @click="$router.push('/userlist/1')">
                        <v-list-tile-action>
                            <v-icon></v-icon>
                        </v-list-tile-action>
                        <v-list-tile-content>
                        <v-list-tile-title>用户列表</v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>
                </v-list-group>
                <v-list-group v-if="storeUser.roles.admin||storeUser.roles.officer">
                    <v-list-tile slot="activator">
                        <v-list-tile-action>
                        <v-icon></v-icon>
                        </v-list-tile-action>
                        <v-list-tile-content>
                        <v-list-tile-title>图书管理</v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>
                    <v-list-tile @click="$router.push('/enter')">
                        <v-list-tile-action>
                            <v-icon></v-icon>
                        </v-list-tile-action>
                        <v-list-tile-content>
                        <v-list-tile-title>入库管理</v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>
                </v-list-group>
                <v-list-tile @click="$router.push('/login')" v-if="storeUser.u_id=='none'">
                    <v-list-tile-action>
                    <v-icon></v-icon>
                    </v-list-tile-action>
                    <v-list-tile-content>
                    <v-list-tile-title>登录</v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>
                
                <v-list-tile @click="$router.push('/register')" v-if="storeUser.u_id=='none'">
                    <v-list-tile-action>
                    <v-icon></v-icon>
                    </v-list-tile-action>
                    <v-list-tile-content>
                    <v-list-tile-title>注册</v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>
                <v-list-tile @click="$router.push('/user')" v-if="storeUser.u_id!='none'">
                    <v-list-tile-action>
                    <v-icon></v-icon>
                    </v-list-tile-action>
                    <v-list-tile-content>
                    <v-list-tile-title>个人中心</v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>
                <v-list-tile @click="logout()" v-if="storeUser.u_id!='none'">
                    <v-list-tile-action>
                    <v-icon></v-icon>
                    </v-list-tile-action>
                    <v-list-tile-content>
                    <v-list-tile-title>注销</v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>
                </v-list>
            </v-navigation-drawer>
            <v-toolbar color="orange" dark fixed app>
                <v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>
                <v-toolbar-title图书管理系统</v-toolbar-title>
            </v-toolbar> 
            <v-content>           
                <transition
                    :name="pageTransitionEffect"
                    @before-enter="handleBeforeEnter"
                    @after-enter="handleAfterEnter">
                    <keep-alive v-if="$route.meta.keepAlive">
                        <transition name="bounce">
                            <router-view class="ma-2"></router-view>
                        </transition>
                    </keep-alive>
                    <transition name="bounce">
                        <router-view class="ma-2"></router-view>
                    </transition>
                </transition>
            </v-content>
            <update-toast></update-toast>
        </v-app>
    </div>
</template>

<script>
import {mapState, mapActions, mapMutations} from 'vuex';
import AppHeader from '@/components/AppHeader';
import AppSidebar from '@/components/AppSidebar';
import UpdateToast from '@/components/UpdateToast';

export default {
    name: 'app',
    components: {
        UpdateToast,
        AppHeader,
        AppSidebar
    },
    data () {
        return {
            drawer: null
        }
    },
    // mounted () {
    //     this.$router.push('/login')
    // },
    computed: {
        ...mapState('pageTransition', {
            pageTransitionType: state => state.type,
            pageTransitionEffect: state => state.effect,
            storeUser: state => state.user,
        }),
        ...mapState('appShell/appHeader', {
            appHeaderShow: state => state.show
        }),

        pageTransitionClass() {
            return `transition-${this.pageTransitionType}`;
        }
    },
    methods: {
        ...mapActions('appShell/appSidebar', [
            'showSidebar',
            'hideSidebar'
        ]),
        ...mapActions('appShell/common', [
            'setPageSwitching'
        ]),
        ...mapMutations('pageTransition', [  
            'setUser'
        ]),
        handleBeforeEnter(el) {
            this.setPageSwitching(true);
        },
        handleAfterEnter(el) {
            this.setPageSwitching(false);
        },
        handleClickHeaderBack() {
            this.$router.go(-1);
        },
        handleClickHeaderMenu() {
            this.showSidebar();
        },
        handleHideSidebar() {
            this.hideSidebar();
        },
        handleShowSidebar() {
            this.showSidebar();
        },
        logout() {
            let tvm = this
            this.axios.get('/api/logout')
            .then(function (response) {
                if (response.status == 200){
                if(response.data.success) {
                    tvm.setUser(
                        { 
                            "u_id": 'none', 
                            "username": 'none', 
                            "roles": { 
                                "admin": false, 
                                "officer": false, 
                                "ordinary": true 
                            }, 
                            "permissions": { 
                                "publish": false, 
                                "praise": false, 
                                "charge": false, 
                                "chargeall": false 
                            } 
                        }
                    )
                    tvm.$router.push('/login')
                } else {
                    alert(response.data.message)
                }
                } else {
                }
            })
            .catch(function (err) {
                console.log(err)
            })
        }
    }
};
</script>

<style>
.list__group:after, .list__group:before {
    display: none;
}
.bounce-enter-active {
  animation: bounce-in .5s;
}
.bounce-leave-active {
  animation: bounce-in .5s reverse;
}
@keyframes bounce-in {
  0% {
    transform: scale(0);
  }
  50% {
    transform: scale(1.5);
  }
  100% {
    transform: scale(1);
  }
}
</style>


<style lang="stylus">
@import '~@/assets/stylus/variable'
#app
    font-family 'Avenir', Helvetica, Arial, sans-serif
    -webkit-font-smoothing antialiased
    -moz-osx-font-smoothing grayscale
    text-align center
    color #2c3e50

    .app-view
        position absolute
        top 0
        right 0
        bottom 0
        left 0
        overflow-x hidden
        overflow-y auto

        &::-webkit-scrollbar
            width 0
            background transparent

        &.app-view-with-header
            top $app-header-height

        &.transition-slide
            transition transform 0.4s cubic-bezier(.55, 0, .1, 1)

            &.slide-left-enter
                transform translate(100%, 0)

            &.slide-right-enter
                transform translate(-100%, 0)

            &.slide-right-leave-active
                transform translate(100%, 0)

            &.slide-left-leave-active
                transform translate(-100%, 0)

        &.transition-fade
            opacity 1
            transition opacity 1s ease

            &.fade-enter
                opacity 0

            &.fade-leave-active
                opacity 0

        // &.transition-slide-fade
        //     &.slide-fade-enter-active
        //         transition: all .3s ease
        //
        //     &.slide-fade-leave-active
        //         transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0)
        //
        //     &.slide-fade-enter,
        //     &.slide-fade-leave-to
        //         transform: translateX(10px)
        //         opacity: 0
</style>
