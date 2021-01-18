package com.dte.security.securityservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GenericResponseModel {

    private String message;

}

import { Component, OnInit } from '@angular/core';
        import { FormGroup, FormBuilder, Validators } from '@angular/forms';
        import { CurrencyPipe } from '@angular/common';
        import { MatStepper } from '@angular/material/stepper';
        import { BlueColourScheme } from 'src/app/layouts/graphs/colour-schemes/colour-schemes';
        import { of } from 'rxjs/internal/observable/of';
        import { Observable } from 'rxjs';

@Component({
        selector: 'app-basic-saving-calculator',
        templateUrl: './basic-saving-calculator.component.html',
        styleUrls: ['./basic-saving-calculator.component.scss']
        })
export class BasicSavingCalculatorComponent implements OnInit {

    savingForm: FormGroup;
    currencySign = '£';

    totalStepsCount: number;
    toggleInput = false;
    resultsMessage;

    initialBalance = 0;
    interestRate;
    interestDuration = 'yearly';
    calculationInterval;
    calculationPeriod = 'yearly';
    postiveTransaction = true;
    postiveTransactions = 0;
    negativeTransactions = 0;
    transactionsPeriod = 'yearly';
    compoundInterval = 'yearly';
    increaseDepositsWithInflation = 0;
    annualInflationRate = 0;
    finalInvestmentValue = 0;
    totalInterestEarned = 0;
    finishedLoadedData = false;
    graphButtonMessage = '';

    showTable = false;

    // might take from application
    timeScale = ['daily', 'weekly', 'monthly', 'yearly'];
    monthlyYearlyTimeScale = ['monthly', 'yearly'];
    dataSource: IntrestDao[];

    // change with resspnse data
    transactions: any[] = [
    { date: '10-02-2020', year_deposits: 0, year_interest: 0, total_deposits: 0, total_interest: 0, balance: 0 },
            ];

    displayedColumns: string[] = ['date', 'year_deposits', 'year_interest', 'total_deposits', 'total_interest', 'balance'];

    // Graph
    colorScheme = BlueColourScheme.colorScheme;

    view: any;
    legend = true;
    showLabels = true;
    animations = true;
    xAxis = true;
    yAxis = true;
    showYAxisLabel = true;
    showXAxisLabel = true;
    xAxisLabel = '';
    yAxisLabel = '';
    timeline = true;

    // TODO change
    graphData = [];

    constructor(private formBuilder: FormBuilder, private currencyPipe: CurrencyPipe) { }

    ngOnInit(): void {
        this.savingForm = this.formBuilder.group({
                initial_balance: [this.initialBalance, [Validators.required]],
        interest_rate: [this.interestRate, [Validators.required, Validators.pattern('^(100|([0-9][0-9]?(\.[0-9]+)?))$')]],
        interest_duration: [this.interestDuration, [Validators.required]],
        calculation_interval: [this.calculationInterval, [Validators.required, Validators.pattern('^(100|([1-9][0-9]?))$')]],
        calculation_period: [this.calculationPeriod, [Validators.required]],
        compound_interval: [this.compoundInterval, [Validators.required]],
        postive_monthly_transaction: [this.postiveTransaction, [Validators.required]],
        postive_transactions: [this.postiveTransactions],
        negative_transactions: [this.negativeTransactions],
        transactions_period: [this.transactionsPeriod, [Validators.required]],
        increase_deposits_with_inflation: [false, [Validators.required]],
        annual_inflation_rate: [this.annualInflationRate]
    });
    }

    /* Reactive form */
    get initial_balance() {
        return this.savingForm.get('initial_balance');
    }

    get interest_rate() {
        return this.savingForm.get('interest_rate');
    }

    get interest_duration() {
        return this.savingForm.get('interest_duration');
    }

    get calculation_interval() {
        return this.savingForm.get('calculation_interval');
    }

    get calculation_period() {
        return this.savingForm.get('calculation_period');
    }

    get compound_interval() {
        return this.savingForm.get('compound_interval');
    }

    get postive_transactions() {
        return this.savingForm.get('postive_transactions');
    }

    get negative_transactions() {
        return this.savingForm.get('negative_transactions');
    }

    get transactions_period() {
        return this.savingForm.get('transactions_period');
    }

    get increase_deposits_with_inflation() {
        return this.savingForm.get('increase_deposits_with_inflation');
    }

    get annual_inflation_rate() {
        return this.savingForm.get('annual_inflation_rate');
    }

    set_initial_balance(event) {
        this.initialBalance = event.target.value;
        this.savingForm.get('initial_balance').setValue(this.currencyPipe.transform(this.initialBalance));
    }

    set_interest_rate(event) {
        this.interestRate = event.target.value;
        this.savingForm.get('interest_rate').setValue(this.currencyPipe.transform(this.interestRate));
    }

    set_interest_duration(event) {
        this.interestDuration = event.target.value;
        this.savingForm.get('interest_duration').setValue(this.currencyPipe.transform(this.interestDuration));
    }

    set_calculation_interval(event) {
        this.calculationInterval = event.target.value;
        this.savingForm.get('calculation_interval').setValue(this.currencyPipe.transform(this.calculationInterval));
    }

    set_compound_period(event) {
        this.calculationPeriod = event.target.value;
        this.savingForm.get('calculation_period').setValue(this.currencyPipe.transform(this.calculationPeriod));
    }

    set_postive_transactions(event) {
        this.postiveTransactions = event.target.value;
        this.savingForm.get('postive_transactions').setValue(this.currencyPipe.transform(this.postiveTransactions));
    }

    set_negative_transactions(event) {
        this.negativeTransactions = event.target.value;
        this.savingForm.get('negative_transactions').setValue(this.currencyPipe.transform(this.negativeTransactions));
    }

    set_transactions_period(event) {
        this.transactionsPeriod = event.target.value;
        this.savingForm.get('transactions_period').setValue(this.currencyPipe.transform(this.transactionsPeriod));
    }

    set_compound_interval(event) {
        this.compoundInterval = event.target.value;
        this.savingForm.get('compound_interval').setValue(this.currencyPipe.transform(this.compoundInterval));
    }

    set_increase_deposits_with_inflation(event) {
        this.increaseDepositsWithInflation = event.target.value;
        this.savingForm.get('increase_deposits_with_inflation').setValue(this.currencyPipe.transform(this.increaseDepositsWithInflation));
    }

    set_annual_inflation_rate(event) {
        this.annualInflationRate = event.target.value;
        this.savingForm.get('annual_inflation_rate').setValue(this.currencyPipe.transform(this.annualInflationRate));
    }

    /* Service call */
    async retreveResults(stepper: MatStepper) {
        this.finishedLoadedData = false;
        stepper.next();
        await this.calculationIntrest().subscribe(res => {
                this.dataSource = res;
    });

        if (this.dataSource.length <= 10) {
            this.showTable = true;
            this.graphButtonMessage = 'Hide graph';
        } else {
            this.graphButtonMessage = 'Show graph';
        }

        this.finishedLoadedData = true;
        this.resultsMessage = this.formmatDurationMessage(this.roundToTwoDp(this.dataSource[this.dataSource.length - 1].balance));
    }

    formmatDurationMessage(balance: number) {
        if (this.calculation_period.value === 'daily') {
            return `In ${this.calculation_interval.value} days, you will have accumlated ${this.currencySign + balance}`;
        } else if (this.calculation_period.value === 'weekly') {
            return `In ${this.calculation_interval.value} weeks, you will have accumlated ${this.currencySign + balance}`;
        } else if (this.calculation_period.value === 'monthly') {
            return `In ${this.calculation_interval.value} month, you will have accumlated ${this.currencySign + balance}`;
        } else if (this.calculation_period.value === 'yearly') {
            return `In ${this.calculation_interval.value} years, you will have accumlated ${this.currencySign + balance}`;
        }
    }

    /* Tables */
    calculateYearlyDepositsTotal() {
        return this.transactions.map(t => t.year_deposits).reduce((acc, value) => acc + value, 0);
    }

    calculateYearIntrestTotal() {
        return this.transactions.map(t => t.year_interest).reduce((acc, value) => acc + value, 0);
    }

    calculateDepositsTotal() {
        return this.transactions[this.transactions.length - 1].total_deposits;
    }

    calculateIntrestTotal() {
        return this.transactions[this.transactions.length - 1].total_interest;
    }

    calculateBalanceTotal() {
        return this.transactions[this.transactions.length - 1].balance;
    }

    // TODO - Resolve weird maths and also the waiting until results are complete
    calculationIntrest(): Observable<IntrestDao[]> {

        let intrestArray: IntrestDao[] = [];

    const contribution: number = this.postiveTransaction ? this.roundToTwoDp(this.postiveTransactions) : -this.roundToTwoDp(this.negativeTransactions);

        // const contribution: number = (this.postiveTransactions - this.negativeTransactions) * 12; // TODO convert "transactionsPeriod" to number
        let total: number = this.initialBalance;
        let totalInterest = 0;

        switch (this.calculation_period.value) {
            case 'daily':
                intrestArray = this.calculationIntrestDaily();
                break;
            case 'weekly':
                this.calculationIntrestWeekly();
                break;
            case 'monthly':
                intrestArray = this.calculationIntrestDaily();
                break;
            case 'yearly':
                intrestArray = this.calculationIntrestDaily();
                break;
        }

        // for (let i = 0; i < this.calculation_interval.value; i++) {
        //   // TODO - change calculation based on "compoundInterval" and convert to number
        //   const startAmountValue: number = this.roundToTwoDp(this.roundToTwoDp(total) + this.roundToTwoDp(contribution));
        //   const interestGained: number =
        //     this.roundToTwoDp(this.roundToTwoDp(startAmountValue) * this.roundToTwoDp(this.interest_rate.value) / 100);
        //   const desposit: number = this.roundToTwoDp(contribution) * this.roundToTwoDp(i + 1) + this.roundToTwoDp(this.initialBalance); // TODO - resolve for time period later and inflation
        //   const endAmountValue: number = this.roundToTwoDp(startAmountValue) + this.roundToTwoDp(interestGained);
        //   totalInterest = this.roundToTwoDp(totalInterest) + this.roundToTwoDp(interestGained);
        //   const timePeriodValue = this.setDate(i + 1);

        //   intrestArray.push(this.populateIntrestDao(timePeriodValue, contribution, interestGained, desposit, totalInterest, endAmountValue));
        //   total = endAmountValue;
        // }


        this.transactions = intrestArray;
        this.finalInvestmentValue = this.roundToTwoDp(intrestArray[intrestArray.length - 1].balance);
        this.totalInterestEarned = this.roundToTwoDp(intrestArray[intrestArray.length - 1].total_interest);
        this.initialBalance = this.roundToTwoDp(this.initialBalance);

        this.graphData = [
        this.populateIntialBalance(this.initialBalance, intrestArray),
                this.populateGraphDeposits(intrestArray),
                this.populateGraphInterest(intrestArray),
    ];
        return of(intrestArray);
    }

    calculationIntrestDaily() {
    const intrestArray: IntrestDao[] = [];
        let total: number = this.initialBalance;
        let totalInterest = 0;
        let contribution: number = this.postiveTransaction ? this.roundToTwoDp(this.postiveTransactions) : -this.roundToTwoDp(this.negativeTransactions);

        if (this.transactions_period.value === 'monthly') { // write for loop for inflaction
            contribution = contribution * 12;
        }

        for (let i = 0; i < this.calculation_interval.value; i++) {
      const desposit: number = this.roundToTwoDp(contribution) * this.roundToTwoDp(i + 1) + this.roundToTwoDp(this.initialBalance);
      const startAmountValue: number = this.roundToTwoDp(this.roundToTwoDp(total) + this.roundToTwoDp(contribution));
      const intrestRate = this.roundToTwoDp(this.interest_rate.value) / 100;
      const yearlyCompoundInterest = (this.roundToTwoDp(startAmountValue) * (1 + intrestRate)) - this.roundToTwoDp(startAmountValue);
      const endAmountValue: number = this.roundToTwoDp(startAmountValue) + this.roundToTwoDp(yearlyCompoundInterest);
      const timePeriodValue = this.setDate(i + 1);
            totalInterest += yearlyCompoundInterest;

            intrestArray.push(this.populateIntrestDao(timePeriodValue, contribution, yearlyCompoundInterest, desposit, totalInterest,
                    endAmountValue));
            total = endAmountValue;
        }
        return intrestArray;
    }

    calculationIntrestWeekly() {
        return null;
    }

    calculationIntrestMonthly() {
        return null;
    }

    calculationIntrestYearly() {
        return null;
    }

    populateIntrestDao(timePeriodValue: string, yearlyDeposit: number, intrestGainedValue: number, totalDesposit: number,
                       interestGained: number, endAmountValue: number): IntrestDao {
    const intrestDao: IntrestDao = {
                date: timePeriodValue,
                year_deposits: yearlyDeposit,
                year_interest: intrestGainedValue,
                total_deposits: totalDesposit,
                total_interest: interestGained,
                balance: endAmountValue
    };
        return intrestDao;
    }

    populateGraphBalance(intrestArray: IntrestDao[], intialBalance: number) {
    const seriesArray: SeriesDao[] = [];
        for (let i = 0; i < intrestArray.length; i++) {

            let balance;
            if (i === 0) {
                balance = this.roundToTwoDp(intrestArray[i].balance) - intrestArray[i].year_interest;
            } else {
                balance = this.roundToTwoDp(intrestArray[i].balance) - intrestArray[i].year_interest - intialBalance;
            }

      const seriesDao: SeriesDao = {
                    name: intrestArray[i].date,
                    value: balance
      };
            seriesArray.push(seriesDao);
        }

    const graphDao: GraphDao = {
                name: 'Balance',
                series: seriesArray
    };
        return graphDao;
    }

    populateGraphDeposits(intrestArray: IntrestDao[]) {
    const seriesArray: SeriesDao[] = [];
        let x = 1;
        for (const i of intrestArray) {
      const seriesDao: SeriesDao = {
                    name: i.date,
                    value: this.roundToTwoDp(i.year_deposits) * x
      };
            seriesArray.push(seriesDao);
            x++;
        }

    const graphDao: GraphDao = {
                name: 'Deposits',
                series: seriesArray
    };
        return graphDao;
    }

    populateGraphInterest(intrestArray: IntrestDao[]) {
    const seriesArray: SeriesDao[] = [];
        for (const i of intrestArray) {
      const seriesDao: SeriesDao = {
                    name: i.date,
                    value: this.roundToTwoDp(i.total_interest)
      };
            seriesArray.push(seriesDao);
        }

    const graphDao: GraphDao = {
                name: 'Interest',
                series: seriesArray
    };
        return graphDao;
    }

    populateIntialBalance(intialBalance: number, intrestArray: IntrestDao[]) {
    const seriesArray: SeriesDao[] = [];
        for (const i of intrestArray) {
      const seriesDao: SeriesDao = {
                    name: i.date,
                    value: this.roundToTwoDp(intialBalance)
      };
            seriesArray.push(seriesDao);
        }

    const graphDao: GraphDao = {
                name: 'Intial Balance',
                series: seriesArray
    };
        return graphDao;
    }

    setTransactionType(type: string) {
        this.postiveTransaction = type === 'negative' ? false : true;
    }

    onResize(event) {
        this.view = [event.target.innerWidth / 1.35, 400];
    }

    roundToTwoDp(value: any): number {
    const response = Number(parseFloat(String(value)).toFixed(2));
        return response;
    }

    toggleTable() {
        this.showTable = !this.showTable;
        this.graphButtonMessage = this.showTable ? 'Hide graph' : 'Show graph';
    }

    setDate(interval: number): string {
    const currentDate = new Date();
        if (this.calculation_period.value === 'daily') {
      const days = new Date(currentDate.setDate(currentDate.getDate() + interval));
      const month = days.getMonth() + 1;
      const date = `${days.getDate()}/${month}/${days.getFullYear()}`;
            return String(date);
        } else if (this.calculation_period.value === 'weekly') {
      const weeks = new Date(currentDate.setDate(currentDate.getDate() + Number(interval * 7)));
      const month = weeks.getMonth() + 1;
      const date = `${weeks.getDate()}/${month}/${weeks.getFullYear()}`;
            return String(date);
        } else if (this.calculation_period.value === 'monthly') {
      const months = this.addMonths(currentDate, interval);
      const month = months.getMonth() + 1;
      const date = `01/${month}/${months.getFullYear()}`;
            return String(date);
        } else if (this.calculation_period.value === 'yearly') {
      const year = new Date().getFullYear() + interval;
            return String(year);
        }
    }

    isLeapYear(year) {
        return (((year % 4 === 0) && (year % 100 !== 0)) || (year % 400 === 0));
    }

    getDaysInMonth(year, month) {
        return [31, (this.isLeapYear(year) ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31][month];
    }

    addMonths(date, value) {
    const d = new Date(date);
    const n = date.getDate();
        d.setDate(1);
        d.setMonth(d.getMonth() + value);
        d.setDate(Math.min(n, this.getDaysInMonth(d.getFullYear(), d.getMonth())));
        return d;
    }
}
interface IntrestDao {
    date: string;
    year_deposits: number;
    year_interest: number;
    total_deposits: number;
    total_interest: number;
    balance: number;
}

interface GraphDao {
    name: string;
    series: SeriesDao[];
}

interface SeriesDao {
    name: string;
    value: number;
}
