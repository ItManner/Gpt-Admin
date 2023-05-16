<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关联用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入关联用户ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户余额" prop="balance">
        <el-input
          v-model="queryParams.balance"
          placeholder="请输入用户余额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="余额过期时间" prop="expiredTime">
        <el-date-picker clearable
          v-model="queryParams.expiredTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择余额过期时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="总的充值金额" prop="totalRecharge">
        <el-input
          v-model="queryParams.totalRecharge"
          placeholder="请输入总的充值金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="总的消费金额" prop="totalConsume">
        <el-input
          v-model="queryParams.totalConsume"
          placeholder="请输入总的消费金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="消费次数" prop="consumptionTimes">
        <el-input
          v-model="queryParams.consumptionTimes"
          placeholder="请输入消费次数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="最后一次消费时间" prop="lastConsumptionTime">
        <el-date-picker clearable
          v-model="queryParams.lastConsumptionTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择最后一次消费时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:balance:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:balance:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:balance:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:balance:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="balanceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="关联用户ID" align="center" prop="userId" />
      <el-table-column label="用户余额" align="center" prop="balance" />
      <el-table-column label="余额过期时间" align="center" prop="expiredTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.expiredTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="总的充值金额" align="center" prop="totalRecharge" />
      <el-table-column label="总的消费金额" align="center" prop="totalConsume" />
      <el-table-column label="消费次数" align="center" prop="consumptionTimes" />
      <el-table-column label="最后一次消费时间" align="center" prop="lastConsumptionTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastConsumptionTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:balance:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:balance:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改用户余额对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="关联用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入关联用户ID" />
        </el-form-item>
        <el-form-item label="用户余额" prop="balance">
          <el-input v-model="form.balance" placeholder="请输入用户余额" />
        </el-form-item>
        <el-form-item label="余额过期时间" prop="expiredTime">
          <el-date-picker clearable
            v-model="form.expiredTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择余额过期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="总的充值金额" prop="totalRecharge">
          <el-input v-model="form.totalRecharge" placeholder="请输入总的充值金额" />
        </el-form-item>
        <el-form-item label="总的消费金额" prop="totalConsume">
          <el-input v-model="form.totalConsume" placeholder="请输入总的消费金额" />
        </el-form-item>
        <el-form-item label="消费次数" prop="consumptionTimes">
          <el-input v-model="form.consumptionTimes" placeholder="请输入消费次数" />
        </el-form-item>
        <el-form-item label="最后一次消费时间" prop="lastConsumptionTime">
          <el-date-picker clearable
            v-model="form.lastConsumptionTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择最后一次消费时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listBalance, getBalance, delBalance, addBalance, updateBalance } from "@/api/system/balance";

export default {
  name: "Balance",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户余额表格数据
      balanceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        balance: null,
        expiredTime: null,
        totalRecharge: null,
        totalConsume: null,
        consumptionTimes: null,
        lastConsumptionTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "关联用户ID不能为空", trigger: "blur" }
        ],
        balance: [
          { required: true, message: "用户余额不能为空", trigger: "blur" }
        ],
        totalRecharge: [
          { required: true, message: "总的充值金额不能为空", trigger: "blur" }
        ],
        totalConsume: [
          { required: true, message: "总的消费金额不能为空", trigger: "blur" }
        ],
        consumptionTimes: [
          { required: true, message: "消费次数不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    open() {
      this.open = true
    },
    close() {
      this.open = false
    },
    /** 查询用户余额列表 */
    getList() {
      this.loading = true;
      listBalance(this.queryParams).then(response => {
        this.balanceList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        userId: null,
        balance: null,
        expiredTime: null,
        totalRecharge: null,
        totalConsume: null,
        consumptionTimes: null,
        lastConsumptionTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加用户余额";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBalance(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户余额";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBalance(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBalance(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除用户余额编号为"' + ids + '"的数据项？').then(function() {
        return delBalance(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/balance/export', {
        ...this.queryParams
      }, `balance_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
