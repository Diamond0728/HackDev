/**
 * @file entry
 * @author diamond(1015678620@qq.com)
 */

import Vue from 'vue';
import Meta from 'vue-meta';

import {createRouter} from '@/.lavas/router';
import {createStore} from '@/.lavas/store';
import AppComponent from './App.vue';
import Vuetify from 'vuetify';

// axios
import axios from 'axios'

Vue.use(Meta);

Vue.use(Vuetify);

Vue.config.productionTip = false;
Vue.prototype.axios = axios

export function createApp() {
    let router = createRouter();
    let store = createStore();
    let App = Vue.extend({
        router,
        store,
        ...AppComponent
    });
    return {App, router, store};
}

if (module.hot) {
    module.hot.accept();
}
