<template>
  <div>
    
    <div style="position: relative;">
      <swiper :options="swiperOption" ref="mySwiper" >
        <swiper-slide v-for="(picture, index) in userMessage.pictures"
          :key="index">
          <div class="swiper-zoom-container">
            <img
            :src="picture">
          </div>
          <!-- <div :style="`background: url(${picture});widht:100%;height:600px;background-size: auto 100%`"></div> -->
        </swiper-slide>
      </swiper>
      <div class="sliderNav" v-if="userMessage.pictures.length > 1">
        <div v-for="(picture, index) in userMessage.pictures" :key="index" v-html="isIndex(showIndex, index)">
        </div>
      </div>
    </div>
    
    <div class="content">
      {{userMessage.content}}
    </div>
    <div style="padding:10px; width:100%;">
      <div style="display:inline-block; width:50%">
        <span class="icon" @click="messageInJike">
          <img style="display:inline-block;vertical-align: top;" src="@/statics/icon/heart.svg">
          <div v-if="parseInt(userMessage.likeCount)>0"
           style="display:inline-block;padding-top:2px;vertical-align: top;font-size:21px;">&nbsp;{{userMessage.likeCount}}</div>
        </span>
        <span class="icon" @click="messageInJike">
          <img style="display:inline-block;vertical-align: top;" src="@/statics/icon/comment.svg">
          <div v-if="parseInt(userMessage.commentCount)>0"
          style="display:inline-block;padding-top:2px;vertical-align: top;font-size:21px;">&nbsp;{{userMessage.commentCount}}</div>
        </span>
        <span class="icon" @click="messageInJike">
          <img src="@/statics/icon/share.svg">
        </span>
      </div>
      <div align="right" class="date">{{formatTime(userMessage.createdAt)}}</div>
    </div>
      <!-- <div style="border-top: 1px solid #ddd;margin: 0px 8px"></div> -->
    <div v-show="false" class="exif">
      <div>
        <span class="icon">
          <img src="@/statics/icon/camera.svg">
        </span>
        <div class="param">
          {{exif.camera}}
        </div>
      </div>
      <div>
        <span class="icon">
          <img src="@/statics/icon/aperture.svg" style="width:20px; padding-left:2px">
        </span>
        <div class="param">
          {{exif.apertrue}}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import dayjs from 'dayjs'
import 'swiper/dist/css/swiper.css'
import { swiper, swiperSlide } from 'vue-awesome-swiper'
export default {
  props: ['userMessage', 'isMe'],
  data() {
    return {
      isLike: false,
      showIndex: 0,
      swiperOption: {
        zoom: true,
      }
    }
  },
  computed: {
    screenWidth: () => document.body.clientWidth,
    screenHeight: () => document.body.clientHeight,
    exif: () => {
      return {
        camera: 'Sony 28T',
        apertrue: '28mm f/5.6 250/1s ISO 800',
        leftSlide: 0,
      }
    },
    swiper() { 
      return this.$refs.mySwiper.swiper
    }
  },
  components: {
    swiper,
    swiperSlide
  },
  mounted() {
    let _self = this
    this.$refs.mySwiper.swiper.on('slideChangeTransitionEnd', function() {
      _self.showIndex = this.activeIndex
    })
  },
  methods: {
    messageInJike() {
      window.location.href =`jike://page.jk/originalPost/${this.userMessage.id}`
      if(!this.isMe)
        this.makeAlert()
    },
    formatTime(date) {
      return dayjs(date).format('DD/MM/YYYY')
    },
    show(index) {
      this.changePointX = this.startPointX
      let slider = this.$refs.slider
      slider.style.marginLeft = `-${this.screenWidth*index}px`
    },
    isIndex(showIndex, index) {
      if(showIndex === index) {
        return '●'
      }
      return '○'
    },
    makeAlert() {
      let options = {
        okText: '加入即刻',
        cancelText: '关闭',
        type: 'softs', 
      }
      this.$dialog
      .confirm('来即刻，和更多有趣的即友相遇', options)
      .then(function(dialog) {
        window.location.href = `https://a.app.qq.com/o/simple.jsp?pkgname=com.ruguoapp.jike&ckey=CK1409335080262`
        console.log(dialog)
      })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../jike-styles.scss';

.icon{
  padding: 0px 5px;
  img{
    width: 24px;
  }
}
.date {
  font-size: $font-para-2-font-size;
  line-height: $font-para-2-line-height;
  text-align: right;
  color: $font-para-2-color;
  letter-spacing: $font-para-2-letter-spacing;
  font-style: $font-para-2-font-style;
  font-weight: $font-para-2-font-weight;
  font-family: $font-para-2-font-family;
  display: inline-block;
  width: 50%;
}
.slider{
  overflow: hidden;
  white-space: nowrap;
  transition: 1s; 
  padding: 0;
  position: relative;
  img {
    display: inline;
  }
}
.sliderNav {
  z-index: 99;
  position: absolute;
  width: 100%;
  text-align: center;
  bottom: 10px;
  div {
    display:inline;
    font-size:16px;
    color: $color-grey-2;
  }
}
.content {
  @include font-para-1;
  padding: 0px 16px;
}
.exif {
  padding: 6px 8px;
  .icon {
    display: inline-block;
    vertical-align: top;
    img{
      width: 24px;
    }
  }
  .param {
    display: inline-block;
    padding-top: 6px;
    padding-left: 8px;
    font-size: 14px;
    vertical-align: top;
  }
}
.swiper-zoom-container {
  width: 100%;
  height: 400px;
  // height: 70vh;
  background: #fff;
  img {
    max-height: 100%;
  }
}
</style>
