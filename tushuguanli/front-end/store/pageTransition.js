/**
 * @file pageTransition module
 * @author lavas
 */

export const state = () => {
    return {
        enable: true,
        type: 'none',
        effect: 'none',
        user: { 
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
        },
        bookinfo: {}
    };
};

export const mutations = {
    setType(state, type) {
        state.type = type;
    },
    setEffect(state, effect) {
        state.effect = effect;
    },
    setUser(state, user) {
        state.user = user
    },
    setBookInfo(state,bookinfo) {
        state.bookinfo = bookinfo
    }
};
