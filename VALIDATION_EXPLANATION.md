# Loan Eligibility Validation - Step by Step

## Example Request
```json
{
  "nationalId": "5566778899",
  "loanAmount": 5000,
  "termInMonths": 10
}
```

## Validation Flow

### Step 1: Input Validation ✅
- Checks all required fields are present
- Validates data types
- **Result:** Request is valid

### Step 2: Calculate Monthly Repayment 💰
**Formula:** `(Loan Amount × 1.10) ÷ Number of Months`

- Loan Amount: 5,000
- Interest (10%): 5,000 × 0.10 = 500
- Total: 5,000 + 500 = 5,500
- Monthly: 5,500 ÷ 10 = **550.00 per month**

### Step 3: Verify Salary 💵
**National ID:** 5566778899
- **Monthly Salary:** 30,000.00
- **Status:** Verified ✅

### Step 4: Check Credit History 📊
**National ID:** 5566778899
- **Credit Score:** 800
- **Active Loans:** 0
- **Has Defaults:** No
- **Status:** Verified ✅

### Step 5: Eligibility Rules Check

#### Rule 1: Salary Requirement ✅
- **Required:** 3 × monthly repayment = 3 × 550 = **1,650**
- **Actual:** 30,000
- **Check:** 30,000 ≥ 1,650 ✅ **PASS**

#### Rule 2: Credit Score ✅
- **Required:** 600 or above
- **Actual:** 800
- **Check:** 800 ≥ 600 ✅ **PASS**

#### Rule 3: No Defaults ✅
- **Required:** No active defaults
- **Actual:** false (no defaults)
- **Check:** No defaults ✅ **PASS**

#### Rule 4: Active Loans Limit ✅
- **Required:** Maximum 3 active loans
- **Actual:** 0 loans
- **Check:** 0 < 3 ✅ **PASS**

### Step 6: Final Decision ✅
**All 4 rules passed → ELIGIBLE**

### Step 7: Save to Database 💾
Application saved with:
- Eligible: true
- Decline Reason: null

### Step 8: Response 📤
```json
{
  "eligible": true,
  "declineReasons": null,
  "monthlyRepayment": 550.00,
  "monthlySalary": 30000.00,
  "creditScore": 800,
  "activeLoansCount": 0,
  "hasActiveDefaults": false
}
```

## Summary
✅ **ELIGIBLE** - All checks passed!

