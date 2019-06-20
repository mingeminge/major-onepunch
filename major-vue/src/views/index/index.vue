<template>
  <div v-loading="loading" style="padding: 10px">
    <el-card>
      <el-row>
        <el-col :md="12" :xs="24" :sm="24">
          <div class="head-avatar">
            <el-image fit="scale-down" style="width: 80px;height: 80px"
                      :src="IMG_URL+avatar"></el-image>
          </div>
          <div class="head-info">
            <div class="head-welcome">
              <span>{{title}}</span>
              <span style="color: #2db7f5">{{info.nickName}}，</span>
              <span>{{message}}</span>
            </div>
            <div class="head-desc">
              {{info.roleName}} | {{info.description}}
            </div>
            <div class="head-time">
              <span>上次登录时间：</span>{{info.lastLoginDate}}
            </div>
          </div>
        </el-col>
        <el-col :md="6" :xs="24" :sm="12">
          <el-col :xs="12" :sm="12" style="padding: 10px">
            <div class="other-info">
              <span>上次登录地点</span>
            </div>
            <div class="other-info">
              <a>{{info.country}}|{{info.region}}|{{info.city}}</a>
            </div>
          </el-col>
          <el-col :xs="12" :sm="8" style="padding: 10px">
            <div class="other-info">
              <span>网络类型</span>
            </div>
            <div class="other-info">
              <a>{{info.isp}}</a>
            </div>
          </el-col>
        </el-col>
        <el-col :md="6" :xs="24" :sm="12">
          <el-col :xs="12" :sm="12" style="padding: 10px">
            <div class="other-info">
              <span>总访问量</span>
            </div>
            <div class="other-info">
              <a>{{info.loginCount}}</a>
            </div>
          </el-col>
          <el-col :xs="12" :sm="8" style="padding: 10px">
            <div class="other-info">
              <span>今日访问量</span>
            </div>
            <div class="other-info">
              <a>{{info.loginCountToday}}</a>
            </div>
          </el-col>
        </el-col>
      </el-row>
    </el-card>
    <el-card style="margin-top: 10px">
      <div id="bar" :style="{height: barHeight,width: '100%'}"></div>
    </el-card>
  </div>


</template>
<script>
  import {getInfo, getLoginChart} from "@/api";
  import {mapGetters} from 'vuex'
  import {isMobile} from "@/utils";

  export default {
    name: "index",//主页
    computed: {
      ...mapGetters([
        'sidebar',
        'avatar',
        'username'
      ]),
      barHeight() {
        if (isMobile()) {
          return window.outerHeight * 0.4 + 'px';
        } else {
          return window.outerHeight * 0.52 + 'px';
        }
      },
      title() {
        let now = new Date(), hour = now.getHours()
        if (hour < 6) {
          return "凌晨好！"
        }
        else if (hour < 9) {
          return "早上好！"
        }
        else if (hour < 12) {
          return "上午好！"
        }
        else if (hour < 14) {
          return "中午好！"
        }
        else if (hour < 17) {
          return "下午好！"
        }
        else if (hour < 19) {
          return "傍晚好！"
        }
        else if (hour < 22) {
          return "晚上好！"
        }
        else {
          return "夜里好！"
        }
      },
      message() {
        let now = new Date(), hour = now.getHours()
        if (hour < 7) {
          return "今天又是充满希望的一天！"
        }
        else if (hour < 12) {
          return "工作之余来杯咖啡吧！"
        }
        else if (hour < 14) {
          return "元气满满的一下午！"
        }
        else if (hour < 17) {
          return "一杯茶一包烟，一个bug改一天！"
        }
        else if (hour < 19) {
          return "bug写完了吗？"
        }
        else if (hour < 22) {
          return "来撸一把？"
        }
        else if (hour < 3) {
          return "睡你麻痹起来嗨啊！"
        }
        else {
          return "哟！修仙呢？"
        }
      },
      isReSize() {
        return this.sidebar.opened;
      }
    },
    watch: {
      isReSize(val) {
        if (this.myChart) {
          //这里有个bug 直接resize无效，定时就行
          setTimeout(() => {
            this.myChart.resize();
          }, 100)
        }
      }
    },
    data() {
      return {
        IMG_URL: process.env.IMG_URL,
        loading: false,
        info: {},
        myChart: null
      }
    },
    mounted() {
      this.getInformation();
      this.getChart();
    },
    methods: {
      getInformation() {
        let _this = this;
        _this.loading = true;
        getInfo(this.username).then(resp => {
          if (resp.code === 200) {
            _this.loading = false;
            _this.info = resp.data;
          }
        })
      },
      getChart() {
        let _this = this;
        getLoginChart(_this.username).then(resp => {
          if (resp.code === 200) {
            let timeArr = [];
            let dataArr1 = [];
            let dataArr2 = [];
            resp.data.forEach(item => {
              timeArr.push(item.time);
              dataArr1.push(item.userVisit);
              dataArr2.push(item.visitCount);
            })
            _this.showChart(timeArr, dataArr1, dataArr2)
          }
        })
      },
      showChart(timeArr, data1, data2) {
        this.myChart = this.$echarts.init(document.getElementById('bar'));
        let option = {
          title: {
            text: '近30日系统访问记录'
          },
          color: ['#3398DB'],
          tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
              type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
          },
          grid: {
            left: '3%',
            right: '5%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              data: timeArr,
              axisTick: {
                alignWithLabel: true
              }
            }
          ],
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              name: '您的访问',
              type: 'bar',
              data: data1,
              itemStyle: {
                color: '#67C23A',
                barBorderRadius: [30, 30, 0, 0]
              }
            },
            {
              name: '总访问',
              type: 'bar',
              data: data2,
              itemStyle: {
                color: '#409EFF',
                barBorderRadius: [30, 30, 0, 0]
              }
            }
          ]
        };
        this.myChart.setOption(option);
        let _this = this;
        window.addEventListener('resize', () => {
          _this.myChart.resize();
        })
      }
    }
  }
</script>

<style scoped>
</style>
